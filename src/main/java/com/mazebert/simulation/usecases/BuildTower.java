package com.mazebert.simulation.usecases;

import com.mazebert.simulation.Card;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.commands.BuildTowerCommand;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.units.towers.Tower;
import com.mazebert.simulation.units.wizards.Wizard;

public strictfp class BuildTower extends Usecase<BuildTowerCommand> {

    private final UnitGateway unitGateway = Sim.context().unitGateway;
    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;

    @Override
    public void execute(BuildTowerCommand command) {
        Wizard wizard = unitGateway.getWizard();

        Card card = wizard.removeCardFromHand(command.cardId);
        if (card instanceof Tower) {
            Tower tower = (Tower) card;
            tower.setX(command.x);
            tower.setY(command.y);
            unitGateway.addUnit(tower);

            simulationListeners.onUnitAdded.dispatch(tower);

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
