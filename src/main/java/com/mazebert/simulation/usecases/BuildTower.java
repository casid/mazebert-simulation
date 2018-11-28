package com.mazebert.simulation.usecases;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.commands.BuildTowerCommand;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.units.items.Item;
import com.mazebert.simulation.units.towers.Tower;
import com.mazebert.simulation.units.wizards.Wizard;

public strictfp class BuildTower extends Usecase<BuildTowerCommand> {

    private final UnitGateway unitGateway = Sim.context().unitGateway;
    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;

    @Override
    public void execute(BuildTowerCommand command) {
        Wizard wizard = unitGateway.getWizard(command.playerId);

        if (wizard.towerStash.remove(command.towerType)) {
            Tower oldTower = unitGateway.findUnit(Tower.class, command.playerId, command.x, command.y);
            if (oldTower != null) {
                unitGateway.removeUnit(oldTower);
                simulationListeners.onUnitRemoved.dispatch(oldTower);
            }

            Tower tower = command.towerType.create();
            tower.setPlayerId(command.playerId);
            tower.setX(command.x);
            tower.setY(command.y);

            unitGateway.addUnit(tower);
            simulationListeners.onUnitAdded.dispatch(tower);

            if (oldTower != null) {
                Item[] items = oldTower.removeAllItems();
                for (int i = 0; i < items.length; ++i) {
                    tower.setItem(i, items[i]);
                }

                tower.setExperience(oldTower.getExperience());
                tower.setKills(oldTower.getKills());
            }

            if (command.onComplete != null) {
                command.onComplete.run();
            }
        } else {
            if (command.onError != null) {
                command.onError.run();
            }
        }
    }

}
