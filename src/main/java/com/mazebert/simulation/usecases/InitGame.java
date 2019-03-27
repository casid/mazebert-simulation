package com.mazebert.simulation.usecases;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.WaveSpawner;
import com.mazebert.simulation.commands.InitGameCommand;
import com.mazebert.simulation.countdown.GameCountDown;
import com.mazebert.simulation.errors.UnsupportedVersionException;
import com.mazebert.simulation.gateways.DifficultyGateway;
import com.mazebert.simulation.gateways.GameGateway;
import com.mazebert.simulation.gateways.WaveGateway;
import com.mazebert.simulation.plugins.random.UuidRandomPlugin;
import com.mazebert.simulation.systems.GameSystem;

public strictfp class InitGame extends Usecase<InitGameCommand> {

    private final UuidRandomPlugin randomPlugin = Sim.context().randomPlugin;
    private final WaveGateway waveGateway = Sim.context().waveGateway;
    private final GameGateway gameGateway = Sim.context().gameGateway;
    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;
    private final GameSystem gameSystem = Sim.context().gameSystem;
    private final DifficultyGateway difficultyGateway = Sim.context().difficultyGateway;

    @Override
    public void execute(InitGameCommand command) {
        if (Sim.context().version != command.version) {
            throw new UnsupportedVersionException("Simulation v" + Sim.context().version + " cannot simulate a game with version v" + command.version);
        }

        randomPlugin.setSeed(command.gameId);
        gameGateway.getGame().id = command.gameId;

        difficultyGateway.setDifficulty(command.difficulty);

        gameSystem.addWizards();

        gameGateway.getGame().map = command.map.create();
        gameGateway.getGame().health = 1.0f;

        if (command.tutorial) {
            gameSystem.initTutorial();
        }

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
