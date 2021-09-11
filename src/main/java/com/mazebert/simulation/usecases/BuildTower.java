package com.mazebert.simulation.usecases;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.commands.BuildTowerCommand;
import com.mazebert.simulation.gateways.GameGateway;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.maps.MapAura;
import com.mazebert.simulation.maps.MapType;
import com.mazebert.simulation.maps.Tile;
import com.mazebert.simulation.systems.LootSystem;
import com.mazebert.simulation.systems.PermanentAbilitySystem;
import com.mazebert.simulation.systems.ProphecySystem;
import com.mazebert.simulation.units.items.Item;
import com.mazebert.simulation.units.items.ItemType;
import com.mazebert.simulation.units.towers.Fox;
import com.mazebert.simulation.units.towers.FoxHunt;
import com.mazebert.simulation.units.towers.Tower;
import com.mazebert.simulation.units.towers.TowerType;
import com.mazebert.simulation.units.wizards.Wizard;

public strictfp class BuildTower implements Usecase<BuildTowerCommand> {

    private final UnitGateway unitGateway = Sim.context().unitGateway;
    private final GameGateway gameGateway = Sim.context().gameGateway;
    private final LootSystem lootSystem = Sim.context().lootSystem;
    private final ProphecySystem prophecySystem = Sim.context().prophecySystem;

    @Override
    public void execute(BuildTowerCommand command) {
        Wizard wizard = unitGateway.getWizard(command.playerId);
        if (wizard == null) {
            return;
        }

        boolean payWithBlood = prophecySystem.isProphecyAvailable(wizard, ItemType.BuildTowerWithBloodProphecy);
        int goldCost = command.towerType.instance().getGoldCost();
        if (wizard.gold < goldCost && !payWithBlood) {
            return;
        }

        Tile tile = getTile(command.x, command.y);
        if (tile == null || !tile.type.buildable) {
            return;
        }

        if (gameGateway.getMap().getType() == MapType.GoldenGrounds && !wizard.ownsFoilCard(command.towerType)) {
            return;
        }

        if (command.towerType == TowerType.SnowGlobe) {
            Tower oldTower = unitGateway.findTower(wizard.getPlayerId(), command.x, command.y);
            if (oldTower == null || oldTower.getRarity() != Rarity.Common) {
                return;
            }
        }

        if (command.towerType == TowerType.BloodDemon && wizard.itemStash.isUniqueAlreadyDropped(ItemType.BloodDemonBlade)) {
            return;
        }

        Tower tower = wizard.towerStash.remove(command.towerType);
        if (tower == null) {
            return;
        }

        if (payWithBlood) {
            prophecySystem.fulfillProphecy(wizard, ItemType.BuildTowerWithBloodProphecy);
        }

        Tower oldTower = summonTower(tower, wizard, command.x, command.y);

        if (payWithBlood) {
            wizard.addHealth(-0.2f);
        } else {
            lootSystem.addGold(wizard, tower, -goldCost);
        }
        if (oldTower != null) {
            lootSystem.addGold(wizard, tower, SellTower.getGoldForSelling(oldTower));
        }
    }

    public Tower summonTower(Tower tower, Wizard wizard, int x, int y) {
        tower.setWizard(wizard);
        tower.setX(x);
        tower.setY(y);

        Item[] items = null;
        Tower oldTower = unitGateway.findTower(wizard.getPlayerId(), x, y);
        if (oldTower != null) {
            items = replace(oldTower, tower);
        }

        Tile tile = getTile(x, y);
        if (tile != null && tile.aura != null) {
            applyAura(tower, tile.aura);
        }

        unitGateway.addUnit(tower);

        if (items != null) {
            transferItems(wizard, tower, items);
        }

        tower.onTowerBuilt.dispatch(oldTower);
        return oldTower;
    }

    private Item[] replace(Tower oldTower, Tower newTower) {
        if (oldTower instanceof Fox) {
            FoxHunt foxHunt = oldTower.getAbility(FoxHunt.class);
            foxHunt.onFoxReplaced();
        }

        oldTower.markForDisposal();

        PermanentAbilitySystem.transferAll(oldTower, newTower);

        Item[] items = oldTower.removeAllItems();

        newTower.setExperience(oldTower.getExperience());
        newTower.setKills(oldTower.getKills());
        newTower.setTotalDamage(oldTower.getTotalDamage());

        unitGateway.removeUnit(oldTower);

        return items;
    }

    private void transferItems(Wizard wizard, Tower tower, Item[] items) {
        for (int i = 0; i < items.length; ++i) {
            Item item = items[i];
            if (item != null) {
                if (isPossibleToTransferItem(tower, item, i)) {
                    tower.setItem(i, item);
                } else if (item.isAllowedToReturnToInventory()) {
                    wizard.itemStash.add(item.getType());
                }
            }
        }
    }

    private boolean isPossibleToTransferItem(Tower tower, Item item, int index) {
        if (tower.isDisposed()) {
            return false;
        }

        if (index >= tower.getInventorySize()) {
            return false;
        }

        if (item.isForbiddenToEquip(tower)) {
            return false;
        }

        if (tower.getItem(index) != null) {
            return false; // already has an item equipped
        }

        return true;
    }

    private Tile getTile(int x, int y) {
        return gameGateway.getMap().getGrid().getTile(x, y);
    }

    private void applyAura(Tower tower, MapAura aura) {
        switch (aura) {
            case IncreasedRange:
                tower.addRange(1);
                break;
            case DecreasedRange:
                tower.addRange(-1);
                break;
            case DamageAgainstAir:
                tower.addDamageAgainstAir(0.2f);
                break;
        }
    }
}
