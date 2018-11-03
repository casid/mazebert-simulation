package com.mazebert.simulation.usecases;

import com.mazebert.simulation.Card;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.commands.BuildTowerCommand;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.units.towers.Tower;
import com.mazebert.simulation.units.wizards.Wizard;
import org.jusecase.inject.Component;

import javax.inject.Inject;

@Component
public strictfp class BuildTower extends Usecase<BuildTowerCommand> {

    @Inject
    private UnitGateway unitGateway;
    @Inject
    private SimulationListeners simulationListeners;

    @Override
    public void execute(BuildTowerCommand command) {
        Wizard wizard = unitGateway.getWizard();

        Card card = wizard.removeCardFromHand(command.cardId);
        if (card instanceof Tower) {
            Tower tower = (Tower)card;
            tower.setX(command.x);
            tower.setY(command.y);
            unitGateway.addUnit(tower);

            simulationListeners.onUnitAdded.dispatch(tower);
        }
    }

}
