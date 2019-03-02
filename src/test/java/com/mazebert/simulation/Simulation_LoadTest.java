package com.mazebert.simulation;

import com.mazebert.simulation.commands.InitGameCommand;
import com.mazebert.simulation.commands.NextWaveCommand;
import com.mazebert.simulation.errors.DsyncException;
import com.mazebert.simulation.gateways.GameGateway;
import com.mazebert.simulation.gateways.PlayerGatewayTrainer;
import com.mazebert.simulation.gateways.TurnGateway;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.projectiles.ProjectileGateway;
import com.mazebert.simulation.replay.ReplayReaderTrainer;
import com.mazebert.simulation.replay.data.ReplayFrame;
import com.mazebert.simulation.replay.data.ReplayTurn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

strictfp class Simulation_LoadTest extends SimTest {

    Simulation simulation;

    CommandExecutorTrainer commandExecutorTrainer = new CommandExecutorTrainer();
    ReplayReaderTrainer replayReaderTrainer = new ReplayReaderTrainer();

    @BeforeEach
    void setUp() {
        commandExecutor = commandExecutorTrainer;
        turnGateway = new TurnGateway(1);
        unitGateway = new UnitGateway();
        simulationListeners = new SimulationListeners();
        projectileGateway = new ProjectileGateway();
        gameGateway = new GameGateway();
        playerGateway = new PlayerGatewayTrainer();

        simulation = new Simulation();
    }

    @Test
    void oneTurn() {
        ReplayFrame turn = new ReplayFrame();
        turn.turnNumber = 0;
        turn.playerTurns = new ReplayTurn[1];
        turn.playerTurns[0] = new ReplayTurn();
        replayReaderTrainer.givenTurn(turn);

        whenSimulationIsLoaded();

        assertThat(simulation.getPlayTimeInSeconds()).isEqualTo(0.1f);
    }

    private void whenSimulationIsLoaded() {
        simulation.load(replayReaderTrainer);
    }

    @Test
    void oneTurn_emptyFrames() {
        ReplayFrame turn = new ReplayFrame();
        turn.turnNumber = 42;
        turn.hash = 1602224128;
        turn.playerTurns = new ReplayTurn[1];
        turn.playerTurns[0] = new ReplayTurn();
        replayReaderTrainer.givenTurn(turn);

        whenSimulationIsLoaded();

        assertThat(simulation.getPlayTimeInSeconds()).isEqualTo(4.2999983f);
    }

    @Test
    void initGameTurn() {
        ReplayFrame turn = new ReplayFrame();
        turn.turnNumber = 0;
        turn.playerTurns = new ReplayTurn[1];
        turn.playerTurns[0] = new ReplayTurn();
        turn.playerTurns[0].commands = new ArrayList<>();
        InitGameCommand initGameCommand = new InitGameCommand();
        initGameCommand.gameId = new UUID(0, 0);
        initGameCommand.rounds = 10;
        turn.playerTurns[0].commands.add(initGameCommand);
        replayReaderTrainer.givenTurn(turn);

        whenSimulationIsLoaded();

        assertThat(simulation.getPlayTimeInSeconds()).isEqualTo(0.1f);
        assertThat(commandExecutorTrainer.getLastCommand()).isSameAs(initGameCommand);
    }

    @Test
    void oneTurn_withCommand() {
        ReplayFrame turn = new ReplayFrame();
        turn.turnNumber = 13;
        turn.hash = 1602224128;
        turn.playerTurns = new ReplayTurn[1];
        turn.playerTurns[0] = new ReplayTurn();
        turn.playerTurns[0].commands = new ArrayList<>();
        turn.playerTurns[0].commands.add(new NextWaveCommand());
        replayReaderTrainer.givenTurn(turn);

        whenSimulationIsLoaded();

        assertThat(commandExecutorTrainer.getLastCommand()).isInstanceOf(NextWaveCommand.class);
        assertThat(commandExecutorTrainer.getLastCommand().turnNumber).isEqualTo(13);
        assertThat(commandExecutorTrainer.getLastCommand().playerId).isEqualTo(1);
        assertThat(simulation.getPlayTimeInSeconds()).isEqualTo(1.4000002f);
    }

    @Test
    void oneTurn_dSync() {
        ReplayFrame turn = new ReplayFrame();
        turn.turnNumber = 50;
        turn.hash = 42;
        turn.playerTurns = new ReplayTurn[1];
        turn.playerTurns[0] = new ReplayTurn();
        replayReaderTrainer.givenTurn(turn);

        Throwable throwable = catchThrowable(this::whenSimulationIsLoaded);

        assertThat(throwable).isInstanceOf(DsyncException.class);
    }

    @Test
    void oneTurn_noPlayerTurns_dSync() {
        ReplayFrame turn = new ReplayFrame();
        turn.turnNumber = 50;
        turn.hash = 42;
        turn.playerTurns = new ReplayTurn[0];
        replayReaderTrainer.givenTurn(turn);

        Throwable throwable = catchThrowable(this::whenSimulationIsLoaded);

        assertThat(throwable).isInstanceOf(DsyncException.class);
    }
}