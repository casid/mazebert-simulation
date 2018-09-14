package com.mazebert.simulation.usecases;

import com.mazebert.simulation.Balancing;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.Wave;
import com.mazebert.simulation.WaveType;
import com.mazebert.simulation.commands.InitGameCommand;
import com.mazebert.simulation.gateways.GameGateway;
import com.mazebert.simulation.gateways.WaveGateway;
import com.mazebert.simulation.plugins.random.RandomPluginTrainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.jusecase.inject.Trainer;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class InitGameTest extends UsecaseTest<InitGameCommand> {

    @Trainer
    RandomPluginTrainer randomPluginTrainer;
    @Trainer
    SimulationListeners simulationListeners;
    @Trainer
    WaveGateway waveGateway;
    @Trainer
    GameGateway gameGateway;

    boolean gameStarted;

    @BeforeEach
    void setUp() {
        usecase = new InitGame();

        request.rounds = 1;
    }

    @Test
    void seedIsSet() {
        request.randomSeed = 1234;
        whenRequestIsExecuted();
        randomPluginTrainer.thenSeedIsSetTo(1234);
    }

    @Test
    void gameCountDownIsStarted() {
        simulationListeners.onGameStarted.add(() -> gameStarted = true);

        whenRequestIsExecuted();
        simulationListeners.onUpdate.dispatch(Balancing.GAME_COUNTDOWN_SECONDS);

        assertThat(gameStarted).isTrue();
        assertThat(simulationListeners.onUpdate.size()).isEqualTo(0);
    }

    @Test
    void gameCountDownIsStarted_notEnoughTimePassed() {
        simulationListeners.onGameStarted.add(() -> gameStarted = true);

        whenRequestIsExecuted();
        simulationListeners.onUpdate.dispatch(0.5f * Balancing.GAME_COUNTDOWN_SECONDS);

        assertThat(gameStarted).isFalse();
    }

    @Test
    void gameCountDownIsStarted_noUpdateYet() {
        simulationListeners.onGameStarted.add(() -> gameStarted = true);

        whenRequestIsExecuted();

        assertThat(gameStarted).isFalse();
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
    void waveGeneration_normal() {
        randomPluginTrainer.givenFloatAbs(0.0f);

        whenRequestIsExecuted();

        Wave wave = waveGateway.getNextWave();
        assertThat(wave.type).isEqualTo(WaveType.Normal);
        assertThat(wave.creepCount).isEqualTo(10);
        assertThat(wave.minSecondsToNextCreep).isEqualTo(1.0f);
        assertThat(wave.maxSecondsToNextCreep).isEqualTo(1.6f);
    }

    @Test
    void waveGeneration_mass() {
        randomPluginTrainer.givenFloatAbs(0.26f);

        whenRequestIsExecuted();

        Wave wave = waveGateway.getNextWave();
        assertThat(wave.type).isEqualTo(WaveType.Mass);
        assertThat(wave.creepCount).isEqualTo(20);
        assertThat(wave.minSecondsToNextCreep).isEqualTo(0.2f);
        assertThat(wave.maxSecondsToNextCreep).isEqualTo(0.6f);
    }

    @Test
    void waveGeneration_boss() {
        randomPluginTrainer.givenFloatAbs(0.51f);

        whenRequestIsExecuted();

        Wave wave = waveGateway.getNextWave();
        assertThat(wave.type).isEqualTo(WaveType.Boss);
        assertThat(wave.creepCount).isEqualTo(1);
    }

    @Test
    void waveGeneration_air() {
        randomPluginTrainer.givenFloatAbs(0.76f);

        whenRequestIsExecuted();

        Wave wave = waveGateway.getNextWave();
        assertThat(wave.type).isEqualTo(WaveType.Air);
        assertThat(wave.creepCount).isEqualTo(5);
        assertThat(wave.minSecondsToNextCreep).isEqualTo(1.6f);
        assertThat(wave.maxSecondsToNextCreep).isEqualTo(3.2f);
    }

    @Test
    void gameId() {
        request.gameId = UUID.fromString("86ce2065-ce3a-46a8-ab3f-ac3179b62002");

        whenRequestIsExecuted();

        assertThat(gameGateway.getGame().id).isEqualTo(request.gameId);
    }
}