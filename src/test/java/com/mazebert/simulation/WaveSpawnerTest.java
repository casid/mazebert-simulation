package com.mazebert.simulation;

import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.gateways.WaveGateway;
import com.mazebert.simulation.plugins.random.RandomPluginTrainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.jusecase.inject.ComponentTest;
import org.jusecase.inject.Trainer;

import static org.assertj.core.api.Assertions.assertThat;

public class WaveSpawnerTest implements ComponentTest {
    @Trainer
    SimulationListeners simulationListeners;
    @Trainer
    UnitGateway unitGateway;
    @Trainer
    WaveGateway waveGateway;
    @Trainer
    RandomPluginTrainer randomPluginTrainer;

    WaveSpawner waveSpawner;

    @BeforeEach
    void setUp() {
        waveSpawner = new WaveSpawner();
    }

    @Test
    void boss() {
        Wave wave = new Wave();
        wave.creepCount = 1;
        waveGateway.addWave(wave);

        whenGameIsStarted();

        assertThat(unitGateway.getUnits()).hasSize(1);
    }

    @Test
    void creeps() {
        Wave wave = new Wave();
        wave.creepCount = 2;
        wave.minSecondsToNextCreep = 1.0f;
        wave.maxSecondsToNextCreep = 1.0f;
        waveGateway.addWave(wave);

        whenGameIsStarted();
        assertThat(unitGateway.getUnits()).hasSize(1);

        whenGameIsUpdated();
        assertThat(unitGateway.getUnits()).hasSize(2);
    }

    @Test
    void creeps_secondsToNextCreep() {
        Wave wave = new Wave();
        wave.creepCount = 2;
        wave.minSecondsToNextCreep = 1.0f;
        wave.maxSecondsToNextCreep = 2.1f;
        waveGateway.addWave(wave);
        randomPluginTrainer.givenFloatAbs(1.0f); // will use maxSecondsToNextCreep

        whenGameIsStarted();
        assertThat(unitGateway.getUnits()).hasSize(1);

        whenGameIsUpdated();
        assertThat(unitGateway.getUnits()).hasSize(1);

        whenGameIsUpdated();
        assertThat(unitGateway.getUnits()).hasSize(1);

        whenGameIsUpdated();
        assertThat(unitGateway.getUnits()).hasSize(2);
    }

    private void whenGameIsStarted() {
        simulationListeners.onGameStarted.dispatch();
        simulationListeners.onUpdate.dispatch(0.0f);
    }

    private void whenGameIsUpdated() {
        simulationListeners.onUpdate.dispatch(1.0f);
    }
}