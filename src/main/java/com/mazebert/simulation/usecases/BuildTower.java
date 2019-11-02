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
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.abilities.StackableAbility;
import com.mazebert.simulation.units.items.Item;
import com.mazebert.simulation.units.towers.Tower;
import com.mazebert.simulation.units.towers.TowerType;
import com.mazebert.simulation.units.wizards.Wizard;

import java.util.ArrayList;
import java.util.List;

public strictfp class BuildTower extends Usecase<BuildTowerCommand> {

    private final UnitGateway unitGateway = Sim.context().unitGateway;
    private final GameGateway gameGateway = Sim.context().gameGateway;
    private final LootSystem lootSystem = Sim.context().lootSystem;

    @Override
    public void execute(BuildTowerCommand command) {
        Wizard wizard = unitGateway.getWizard(command.playerId);
        if (wizard == null) {
            return;
        }

        int goldCost = command.towerType.instance().getGoldCost();
        if (wizard.gold < goldCost) {
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

        Tower tower = wizard.towerStash.remove(command.towerType);
        if (tower == null) {
            return;
        }

        Tower oldTower = summonTower(tower, wizard, command.x, command.y);

        lootSystem.addGold(wizard, tower, -goldCost);
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

        if (oldTower != null) {
            tower.onTowerReplaced.dispatch(oldTower);
        }

        return oldTower;
    }

    private Item[] replace(Tower oldTower, Tower newTower) {
        List<Ability> permanentAbilities = new ArrayList<>();
        oldTower.forEachAbility(ability -> {
            if (ability.isPermanent()) {
                permanentAbilities.add(ability);
            }
        });

        for (Ability permanentAbility : permanentAbilities) {
            if (permanentAbility instanceof StackableAbility) {
                do {
                    transferPermanentAbility(oldTower, newTower, permanentAbility);
                } while (permanentAbility.getUnit() != null);
            } else {
                transferPermanentAbility(oldTower, newTower, permanentAbility);
            }
        }

        Item[] items = oldTower.removeAllItems();

        newTower.setExperience(oldTower.getExperience());
        newTower.setKills(oldTower.getKills());
        newTower.setTotalDamage(oldTower.getTotalDamage());

        unitGateway.removeUnit(oldTower);

        return items;
    }

    private void transferPermanentAbility(Tower oldTower, Tower newTower, Ability permanentAbility) {
        oldTower.removeAbility(permanentAbility);
        newTower.addAbility(permanentAbility);
    }

    private void transferItems(Wizard wizard, Tower tower, Item[] items) {
        for (int i = 0; i < items.length; ++i) {
            Item item = items[i];
            if (item != null) {
                if (i >= tower.getInventorySize() || item.isForbiddenToEquip(tower) || tower.getItem(i) != null) {
                    wizard.itemStash.add(item.getType());
                } else {
                    tower.setItem(i, item);
                }
            }
        }
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
