package com.mazebert.simulation.usecases;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.WaveSpawner;
import com.mazebert.simulation.commands.InitGameCommand;
import com.mazebert.simulation.countdown.GameCountDown;
import com.mazebert.simulation.gateways.GameGateway;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.gateways.WaveGateway;
import com.mazebert.simulation.maps.BloodMoor;
import com.mazebert.simulation.plugins.random.UuidRandomPlugin;
import com.mazebert.simulation.units.towers.TowerType;
import com.mazebert.simulation.units.wizards.Wizard;

public strictfp class InitGame extends Usecase<InitGameCommand> {

    private final UuidRandomPlugin randomPlugin = Sim.context().randomPlugin;
    private final WaveGateway waveGateway = Sim.context().waveGateway;
    private final GameGateway gameGateway = Sim.context().gameGateway;
    private final UnitGateway unitGateway = Sim.context().unitGateway;
    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;

    @Override
    public void execute(InitGameCommand command) {
        randomPlugin.setSeed(command.gameId);
        gameGateway.getGame().id = command.gameId;

        if (unitGateway.getWizard() == null) {
            Wizard wizard = new Wizard();
            for (int i = 0; i < 2; ++i) {
                wizard.towerStash.add(TowerType.Hitman);
            }
            for (int i = 0; i < 2; ++i) {
                wizard.towerStash.add(TowerType.Dandelion);
            }
            unitGateway.addUnit(wizard);
        }

        gameGateway.getGame().map = new BloodMoor();
        gameGateway.getGame().health = 1.0f;

        if (command.rounds > 0) {
            waveGateway.setTotalWaves(command.rounds);
            waveGateway.generateMissingWaves(randomPlugin);

            new GameCountDown().start();
            new WaveSpawner();
        }

        simulationListeners.onGameInitialized.dispatch();
    }

}
