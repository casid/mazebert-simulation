package com.mazebert.simulation.usecases;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.commands.BuildTowerCommand;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.items.Item;
import com.mazebert.simulation.units.towers.Tower;
import com.mazebert.simulation.units.wizards.Wizard;

import java.util.ArrayList;
import java.util.List;

public strictfp class BuildTower extends Usecase<BuildTowerCommand> {

    private final UnitGateway unitGateway = Sim.context().unitGateway;

    @Override
    public void execute(BuildTowerCommand command) {
        Wizard wizard = unitGateway.getWizard(command.playerId);

        if (wizard.towerStash.remove(command.towerType)) {
            Tower tower = command.towerType.create();
            tower.setWizard(wizard);
            tower.setX(command.x);
            tower.setY(command.y);

            Tower oldTower = unitGateway.findUnit(Tower.class, command.playerId, command.x, command.y);
            if (oldTower != null) {
                replace(oldTower, tower);
            }

            unitGateway.addUnit(tower);

            if (command.onComplete != null) {
                command.onComplete.run();
            }
        } else {
            if (command.onError != null) {
                command.onError.run();
            }
        }
    }

    private void replace(Tower oldTower, Tower newTower) {
        List<Ability> permanentAbilities = new ArrayList<>();
        oldTower.forEachAbility(ability -> {
            if (ability.isPermanent()) {
                permanentAbilities.add(ability);
            }
        });

        for (Ability permanentAbility : permanentAbilities) {
            do {
                oldTower.removeAbility(permanentAbility);
                newTower.addAbility(permanentAbility);
            } while (permanentAbility.getUnit() != null);
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
}
