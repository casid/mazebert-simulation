package com.mazebert.simulation;

import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.gateways.WaveGateway;
import com.mazebert.simulation.plugins.random.RandomPluginTrainer;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.creeps.CreepState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.jusecase.inject.ComponentTest;
import org.jusecase.inject.Trainer;

import java.util.concurrent.atomic.AtomicReference;

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
        waveGateway.setTotalWaves(250);
    }

    @Test
    void noWaveExists() {
        whenGameIsStarted();
        assertThat(unitGateway.getUnits()).hasSize(0);
        assertThat(waveGateway.getCurrentWave()).isEqualTo(0);
    }

    @Test
    void waveIsRemoved() {
        Wave wave = new Wave();
        wave.creepCount = 1;
        waveGateway.addWave(wave);

        whenGameIsStarted();

        assertThat(waveGateway.getWaves().stream().findFirst().orElse(null)).isNotEqualTo(wave);
        assertThat(waveGateway.getCurrentWave()).isEqualTo(1);
    }

    @Test
    void wavesAreGenerated() {
        Wave wave = new Wave();
        wave.creepCount = 1;
        waveGateway.addWave(wave);

        whenGameIsStarted();

        assertThat(waveGateway.getWaves()).hasSize(5);
    }

    @Test
    void wavesAreGenerated_notMoreThanMax() {
        waveGateway.setTotalWaves(5);
        Wave wave = new Wave();
        wave.creepCount = 1;
        waveGateway.addWave(wave);

        whenGameIsStarted();

        assertThat(waveGateway.getWaves()).hasSize(4);
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
        randomPluginTrainer.givenFloatAbs(0.99f); // will use maxSecondsToNextCreep

        whenGameIsStarted();
        assertThat(unitGateway.getUnits()).hasSize(1);

        whenGameIsUpdated();
        assertThat(unitGateway.getUnits()).hasSize(1);

        whenGameIsUpdated();
        assertThat(unitGateway.getUnits()).hasSize(1);

        whenGameIsUpdated();
        assertThat(unitGateway.getUnits()).hasSize(2);
    }

    @Test
    void creepIsKilled() {
        AtomicReference<Unit> removedUnit = new AtomicReference<>(null);
        simulationListeners.onUnitRemoved.add(removedUnit::set);
        Wave wave = new Wave();
        wave.creepCount = 1;
        waveGateway.addWave(wave);

        whenGameIsStarted();
        assertThat(unitGateway.getUnits()).hasSize(1);
        Creep creep = getCreep(0);
        creep.setHealth(0.0f);
        assertThat(unitGateway.getUnits()).hasSize(1);
        creep.simulate(1.0f);
        assertThat(unitGateway.getUnits()).hasSize(1);
        creep.simulate(1.0f);

        assertThat(creep.getState()).isEqualTo(CreepState.Dead);
        assertThat(unitGateway.getUnits()).isEmpty();
        assertThat(removedUnit.get()).isSameAs(creep);
    }

    @Test
    void creepIsKilled_onDeath() {
        AtomicReference<Creep> deathCreep = new AtomicReference<>(null);
        Wave wave = new Wave();
        wave.creepCount = 1;
        waveGateway.addWave(wave);

        whenGameIsStarted();
        Creep creep = getCreep(0);
        creep.onDeath.add(deathCreep::set);
        creep.setHealth(0.0f);

        assertThat(deathCreep.get()).isSameAs(creep);
    }

    @Test
    void gold_boss() {
        Wave wave = new Wave();
        wave.creepCount = 1;
        waveGateway.addWave(wave);

        whenAllCreepsAreSpawned();

        assertThat(getCreep(0).getGold()).isEqualTo(51);
    }

    @Test
    void gold_boss_higherLevel() {
        waveGateway.setCurrentWave(9);
        Wave wave = new Wave();
        wave.creepCount = 1;
        waveGateway.addWave(wave);

        whenAllCreepsAreSpawned();

        assertThat(getCreep(0).getGold()).isEqualTo(57);
    }

    @Test
    void gold_distributed() {
        Wave wave = new Wave();
        wave.creepCount = 2;
        waveGateway.addWave(wave);
        randomPluginTrainer.givenFloatAbs(0.9f);

        whenAllCreepsAreSpawned();

        assertThat(getCreep(0).getGold()).isEqualTo(25);
        assertThat(getCreep(1).getGold()).isEqualTo(26);
    }

    private Creep getCreep(int index) {
        return (Creep)unitGateway.getUnits().get(index);
    }

    private void whenGameIsStarted() {
        simulationListeners.onGameStarted.dispatch();
        simulationListeners.onUpdate.dispatch(0.0f);
    }

    private void whenAllCreepsAreSpawned() {
        simulationListeners.onGameStarted.dispatch();
        for (int i = 0; i < 10; ++i) {
            simulationListeners.onUpdate.dispatch(1.0f);
        }
    }

    private void whenGameIsUpdated() {
        simulationListeners.onUpdate.dispatch(1.0f);
    }
}