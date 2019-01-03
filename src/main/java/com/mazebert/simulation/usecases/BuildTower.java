package com.mazebert.simulation.usecases;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.commands.BuildTowerCommand;
import com.mazebert.simulation.gateways.UnitGateway;
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

        Tower tower = wizard.towerStash.remove(command.towerType);
        if (tower == null) {
            return;
        }

        summonTower(tower, wizard, command.x, command.y);

        lootSystem.addGold(wizard, tower, -goldCost);
    }

    public void summonTower(Tower tower, Wizard wizard, int x, int y) {
        tower.setWizard(wizard);
        tower.setX(x);
        tower.setY(y);

        Tower oldTower = unitGateway.findTower(wizard.getPlayerId(), x, y);
        if (oldTower != null) {
            replace(oldTower, tower);
        }

        unitGateway.addUnit(tower);
    }

    private void replace(Tower oldTower, Tower newTower) {
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
            newTower.setItem(i, items[i]);
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
