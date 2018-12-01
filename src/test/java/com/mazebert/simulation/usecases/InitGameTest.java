package com.mazebert.simulation.usecases;

import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.commands.InitGameCommand;
import com.mazebert.simulation.gateways.*;
import com.mazebert.simulation.maps.BloodMoor;
import com.mazebert.simulation.plugins.random.RandomPluginTrainer;
import com.mazebert.simulation.units.wizards.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.mazebert.simulation.Balancing.GAME_COUNTDOWN_SECONDS;
import static org.assertj.core.api.Assertions.assertThat;

class InitGameTest extends UsecaseTest<InitGameCommand> {

    RandomPluginTrainer randomPluginTrainer = new RandomPluginTrainer();

    boolean gameStarted;

    @BeforeEach
    void setUp() {
        randomPlugin = randomPluginTrainer;
        simulationListeners = new SimulationListeners();
        playerGateway = new PlayerGatewayTrainer();
        waveGateway = new WaveGateway();
        gameGateway = new GameGateway();
        unitGateway = new UnitGateway();
        difficultyGateway = new DifficultyGateway();

        usecase = new InitGame();

        request.playerId = 1;
        request.rounds = 1;
    }

    @Test
    void seedIsSet() {
        request.gameId = UUID.randomUUID();
        whenRequestIsExecuted();
        randomPluginTrainer.thenSeedIsSetTo(request.gameId);
    }

    @Test
    void healthIsSet() {
        whenRequestIsExecuted();
        assertThat(gameGateway.getGame().health).isEqualTo(1.0f);
    }

    @Test
    void gameCountDownIsStarted() {
        simulationListeners.onGameStarted.add(() -> gameStarted = true);

        whenRequestIsExecuted();
        simulationListeners.onUpdate.dispatch(GAME_COUNTDOWN_SECONDS);

        assertThat(gameStarted).isTrue();
        assertThat(simulationListeners.onUpdate.size()).isEqualTo(1);
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
        request.rounds = 250;
        whenRequestIsExecuted();
        assertThat(waveGateway.getWaves()).hasSize(WaveGateway.WAVES_IN_ADVANCE);
    }

    @Test
    void wavesAreGenerated_notMoreThanTotalWaveCount() {
        request.rounds = 2;
        whenRequestIsExecuted();
        assertThat(waveGateway.getWaves()).hasSize(request.rounds);
    }

    @Test
    void gameId() {
        request.gameId = UUID.fromString("86ce2065-ce3a-46a8-ab3f-ac3179b62002");

        whenRequestIsExecuted();

        assertThat(gameGateway.getGame().id).isEqualTo(request.gameId);
    }

    @Test
    void noRounds_meansWizardTower() {
        request.rounds = 0;
        whenRequestIsExecuted();
        assertThat(simulationListeners.onUpdate.size()).isEqualTo(0);
    }

    @Test
    void firstGame() {
        whenRequestIsExecuted();

        Wizard wizard = unitGateway.getWizard(request.playerId);
        assertThat(wizard).isNotNull();
        assertThat(wizard.towerStash.size()).isGreaterThan(0);
        //assertThat(wizard.towerStash.get(0).getCardType()).isEqualTo(TowerType.Frog);
        //assertThat(wizard.towerStash.get(0).getAmount()).isEqualTo(3);
        // TODO re-activate when calculation starting towers
    }

    @Test
    void map() {
        whenRequestIsExecuted();

        assertThat(gameGateway.getGame().map).isInstanceOf(BloodMoor.class);
    }
}