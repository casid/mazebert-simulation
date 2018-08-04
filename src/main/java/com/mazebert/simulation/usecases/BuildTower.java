package com.mazebert.simulation.usecases;

import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.plugins.random.RandomPlugin;
import com.mazebert.simulation.commands.BuildTowerCommand;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.units.towers.Hitman;
import com.mazebert.simulation.units.towers.Tower;
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
        Tower tower = new Hitman(); // TODO
        tower.setX(command.x);
        tower.setY(command.y);
        unitGateway.addUnit(tower);

        simulationListeners.onTowerBuilt.dispatch(tower);
    }

}
