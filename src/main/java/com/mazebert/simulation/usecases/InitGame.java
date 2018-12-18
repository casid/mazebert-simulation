package com.mazebert.simulation.usecases;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.WaveSpawner;
import com.mazebert.simulation.commands.InitGameCommand;
import com.mazebert.simulation.countdown.GameCountDown;
import com.mazebert.simulation.gateways.GameGateway;
import com.mazebert.simulation.gateways.WaveGateway;
import com.mazebert.simulation.maps.BloodMoor;
import com.mazebert.simulation.plugins.random.UuidRandomPlugin;
import com.mazebert.simulation.systems.GameSystem;

public strictfp class InitGame extends Usecase<InitGameCommand> {

    private final UuidRandomPlugin randomPlugin = Sim.context().randomPlugin;
    private final WaveGateway waveGateway = Sim.context().waveGateway;
    private final GameGateway gameGateway = Sim.context().gameGateway;
    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;
    private final GameSystem gameSystem = Sim.context().gameSystem;

    @Override
    public void execute(InitGameCommand command) {
        randomPlugin.setSeed(command.gameId);
        gameGateway.getGame().id = command.gameId;

        gameSystem.addWizards();

        gameGateway.getGame().map = new BloodMoor();
        gameGateway.getGame().health = 1.0f;

        if (command.rounds > 0) {
            waveGateway.setTotalWaves(command.rounds);
            waveGateway.generateMissingWaves(randomPlugin);

            Sim.context().gameCountDown = new GameCountDown();
            Sim.context().waveSpawner = new WaveSpawner();

            Sim.context().gameCountDown.start();
        }

        simulationListeners.onGameInitialized.dispatch();
    }

}
