package com.mazebert.simulation.usecases;

import com.mazebert.simulation.plugins.random.RandomPlugin;
import com.mazebert.simulation.commands.BuildTowerCommand;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.units.towers.Tower;
import org.jusecase.inject.Component;

import javax.inject.Inject;

@Component
public strictfp class BuildTower extends Usecase<BuildTowerCommand> {

    @Inject
    private UnitGateway unitGateway;
    @Inject
    private RandomPlugin randomPlugin;

    @Override
    public void execute(BuildTowerCommand command) {
        System.out.println("Player " + command.playerId + " builds tower " + command.tower + " on turn " + command.turnNumber);
        System.out.println("Random number " + randomPlugin.nextInt());

        Tower tower = new Tower();
        unitGateway.addUnit(tower);
    }

}
