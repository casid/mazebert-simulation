package com.mazebert.simulation.usecases;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.commands.BuildTowerCommand;
import com.mazebert.simulation.gateways.GameGateway;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.maps.Tile;
import com.mazebert.simulation.systems.LootSystem;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.abilities.StackableAbility;
import com.mazebert.simulation.units.items.Item;
import com.mazebert.simulation.units.towers.Tower;
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

        int goldCost = command.towerType.instance.getGoldCost();
        if (wizard.gold < goldCost) {
            return;
        }

        Tile tile = gameGateway.getMap().getGrid().getTile(command.x, command.y);
        if (tile == null || !tile.type.buildable) {
            return;
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

        Tower oldTower = unitGateway.findTower(wizard.getPlayerId(), x, y);
        if (oldTower != null) {
            replace(wizard, oldTower, tower);
        }

        unitGateway.addUnit(tower);

        return oldTower;
    }

    private void replace(Wizard wizard, Tower oldTower, Tower newTower) {
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
        for (int i = 0; i < items.length; ++i) {
            Item item = items[i];
            if (item != null) {
                if (i >= newTower.getInventorySize() || item.isForbiddenToEquip(newTower)) {
                    wizard.itemStash.add(item.getType());
                } else {
                    newTower.setItem(i, item);
                }
            }
        }

        newTower.setExperience(oldTower.getExperience());
        newTower.setKills(oldTower.getKills());
        newTower.setTotalDamage(oldTower.getTotalDamage());

        unitGateway.removeUnit(oldTower);
    }

    private void transferPermanentAbility(Tower oldTower, Tower newTower, Ability permanentAbility) {
        oldTower.removeAbility(permanentAbility);
        newTower.addAbility(permanentAbility);
    }
}
