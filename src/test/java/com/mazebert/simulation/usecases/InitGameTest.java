package com.mazebert.simulation.usecases;

import com.mazebert.simulation.Difficulty;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.commands.InitGameCommand;
import com.mazebert.simulation.errors.UnsupportedVersionException;
import com.mazebert.simulation.gateways.*;
import com.mazebert.simulation.maps.BloodMoor;
import com.mazebert.simulation.maps.MapType;
import com.mazebert.simulation.plugins.random.RandomPluginTrainer;
import com.mazebert.simulation.systems.GameSystem;
import com.mazebert.simulation.systems.LootSystem;
import com.mazebert.simulation.units.wizards.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.mazebert.simulation.Balancing.GAME_COUNTDOWN_SECONDS;
import static com.mazebert.simulation.Balancing.STARTING_GOLD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class InitGameTest extends UsecaseTest<InitGameCommand> {
    RandomPluginTrainer randomPluginTrainer = new RandomPluginTrainer();
    PlayerGatewayTrainer playerGatewayTrainer = new PlayerGatewayTrainer();

    boolean gameStarted;

    @BeforeEach
    void setUp() {
        randomPlugin = randomPluginTrainer;
        simulationListeners = new SimulationListeners();
        playerGateway = playerGatewayTrainer;
        gameGateway = new GameGateway();
        waveGateway = new WaveGateway();
        gameGateway = new GameGateway();
        unitGateway = new UnitGateway();
        difficultyGateway = new DifficultyGateway();
        lootSystem = new LootSystem();
        gameSystem = new GameSystem();

        usecase = new InitGame();

        command.playerId = 1;
        command.rounds = 1;
        command.difficulty = Difficulty.Normal;
        command.map = MapType.BloodMoor;
    }

    @Test
    void seedIsSet() {
        command.gameId = new UUID(1, 2);
        whenRequestIsExecuted();
        randomPluginTrainer.thenSeedIsSetTo(command.gameId);
    }

    @Test
    void healthIsSet() {
        whenRequestIsExecuted();
        assertThat(gameGateway.getGame().health).isEqualTo(1.0f);
    }

    @Test
    void timestampIsSet() {
        command.timestamp = 123413513513L;
        whenRequestIsExecuted();
        assertThat(gameGateway.getGame().timestamp).isEqualTo(command.timestamp);
    }

    @Test
    void gameCountDownIsStarted() {
        simulationListeners.onGameStarted.add(() -> gameStarted = true);

        whenRequestIsExecuted();
        simulationListeners.onUpdate.dispatch(GAME_COUNTDOWN_SECONDS);

        assertThat(gameStarted).isTrue();
        assertThat(simulationListeners.onUpdate.size()).isEqualTo(2);
    }

    @Test
    void gameCountDownIsStarted_notEnoughTimePassed() {
        simulationListeners.onGameStarted.add(() -> gameStarted = true);

        whenRequestIsExecuted();
        simulationListeners.onUpdate.dispatch(0.5f * GAME_COUNTDOWN_SECONDS);

        assertThat(gameStarted).isFalse();
    }

    @Test
    void gameCountDownIsStarted_noUpdateYet() {
        simulationListeners.onGameStarted.add(() -> gameStarted = true);

        whenRequestIsExecuted();

        assertThat(gameStarted).isFalse();
    }

    @Test
    void gameCountDownIsUpdated() {
        List<Integer> countDown = new ArrayList<>();
        simulationListeners.onGameCountDown.add(countDown::add);

        whenRequestIsExecuted();
        simulationListeners.onUpdate.dispatch(1.0f);

        assertThat(countDown).containsExactly((int) GAME_COUNTDOWN_SECONDS, (int) GAME_COUNTDOWN_SECONDS - 1);
    }

    @Test
    void gameCountDownIsUpdated_onlyWhenSecondsChange() {
        List<Integer> countDown = new ArrayList<>();
        simulationListeners.onGameCountDown.add(countDown::add);

        whenRequestIsExecuted();
        simulationListeners.onUpdate.dispatch(0.25f);
        simulationListeners.onUpdate.dispatch(0.25f);
        simulationListeners.onUpdate.dispatch(0.25f);
        simulationListeners.onUpdate.dispatch(0.25f);

        assertThat(countDown).containsExactly((int) GAME_COUNTDOWN_SECONDS, (int) GAME_COUNTDOWN_SECONDS - 1);
    }

    @Test
    void gameCountDownIsUpdated_rounding() {
        List<Integer> countDown = new ArrayList<>();
        simulationListeners.onGameCountDown.add(countDown::add);

        whenRequestIsExecuted();
        simulationListeners.onUpdate.dispatch(0.5f);
        simulationListeners.onUpdate.dispatch(0.5001f);

        assertThat(countDown).containsExactly((int) GAME_COUNTDOWN_SECONDS, (int) GAME_COUNTDOWN_SECONDS - 1);
    }

    @Test
    void wavesAreGenerated() {
        command.rounds = 250;
        whenRequestIsExecuted();
        assertThat(waveGateway.getWaves()).hasSize(WaveGateway.WAVES_IN_ADVANCE);
    }

    @Test
    void wavesAreGenerated_notMoreThanTotalWaveCount() {
        command.rounds = 2;
        whenRequestIsExecuted();
        assertThat(waveGateway.getWaves()).hasSize(command.rounds);
    }

    @Test
    void gameId() {
        command.gameId = UUID.fromString("86ce2065-ce3a-46a8-ab3f-ac3179b62002");

        whenRequestIsExecuted();

        assertThat(gameGateway.getGame().id).isEqualTo(command.gameId);
    }

    @Test
    void noRounds_meansWizardTower() {
        command.rounds = 0;
        whenRequestIsExecuted();
        assertThat(simulationListeners.onUpdate.size()).isEqualTo(0);
    }

    @Test
    void gameEnd() {
        whenRequestIsExecuted();
        Wizard wizard = unitGateway.getWizard(command.playerId);
        wizard.addHealth(-1.0f);

        assertThat(gameGateway.getGame().health).isEqualTo(0);
    }

    @Test
    void healthLost_player1() {
        playerGatewayTrainer.givenPlayerCount(2);

        whenRequestIsExecuted();
        Wizard wizard = unitGateway.getWizard(command.playerId);
        wizard.addHealth(-1.0f);

        assertThat(gameGateway.getGame().health).isEqualTo(0.5f);
    }

    @Test
    void startingGold() {
        randomPluginTrainer.givenFloatAbs(0.0f);

        whenRequestIsExecuted();

        Wizard wizard = unitGateway.getWizard(command.playerId);
        assertThat(wizard).isNotNull();
        assertThat(wizard.gold).isEqualTo(STARTING_GOLD);
    }

    @Test
    void map() {
        whenRequestIsExecuted();
        assertThat(gameGateway.getGame().map).isInstanceOf(BloodMoor.class);
    }

    @Test
    void difficulty() {
        command.difficulty = Difficulty.Hell;
        whenRequestIsExecuted();
        assertThat(difficultyGateway.getDifficulty()).isEqualTo(Difficulty.Hell);
    }

    @Test
    void wrongVersion() {
        command.version = 0;
        Throwable throwable = catchThrowable(this::whenRequestIsExecuted);
        assertThat(throwable).isInstanceOf(UnsupportedVersionException.class);
    }

    @Test
    void tutorial() {
        command.tutorial = true;
        whenRequestIsExecuted();
        assertThat(gameSystem.isTutorial()).isTrue();
    }

    @Override
    protected InitGameCommand createCommand() {
        return new InitGameCommand();
    }
}