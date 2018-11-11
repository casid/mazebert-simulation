package com.mazebert.simulation;

import com.mazebert.simulation.gateways.DifficultyGateway;
import com.mazebert.simulation.gateways.GameGateway;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.gateways.WaveGateway;
import com.mazebert.simulation.maps.BloodMoor;
import com.mazebert.simulation.plugins.random.RandomPluginTrainer;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.creeps.CreepState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;

public class WaveSpawnerTest extends SimTest {

    RandomPluginTrainer randomPluginTrainer = new RandomPluginTrainer();

    WaveSpawner waveSpawner;

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();
        unitGateway = new UnitGateway();
        waveGateway = new WaveGateway();
        randomPlugin = randomPluginTrainer;
        difficultyGateway = new DifficultyGateway();
        gameGateway = new GameGateway();

        gameGateway.getGame().map = new BloodMoor();

        waveSpawner = new WaveSpawner();
        waveGateway.setTotalWaves(250);
    }

    @Test
    void noWaveExists() {
        whenGameIsStarted();
        assertThat(unitGateway.getUnits()).hasSize(0);
        assertThat(waveGateway.getCurrentRound()).isEqualTo(0);
    }

    @Test
    void waveIsRemoved() {
        Wave wave = new Wave();
        wave.creepCount = 1;
        waveGateway.addWave(wave);

        whenGameIsStarted();

        assertThat(waveGateway.getWaves().stream().findFirst().orElse(null)).isNotEqualTo(wave);
        assertThat(waveGateway.getCurrentRound()).isEqualTo(1);
    }

    @Test
    void wavesAreGenerated() {
        givenBossWave();

        whenGameIsStarted();

        assertThat(waveGateway.getWaves()).hasSize(5);
    }

    @Test
    void wavesAreGenerated_notMoreThanMax() {
        waveGateway.setTotalWaves(5);
        givenBossWave();

        whenGameIsStarted();

        assertThat(waveGateway.getWaves()).hasSize(4);
    }

    @Test
    void wavesAreGenerated_round() {
        givenBossWave();

        whenGameIsStarted();

        List<Wave> waves = new ArrayList<>(waveGateway.getWaves());
        assertThat(waves.get(0).round).isEqualTo(1);
        assertThat(waves.get(1).round).isEqualTo(2);
        assertThat(waves.get(2).round).isEqualTo(3);
        assertThat(waves.get(3).round).isEqualTo(4);
        assertThat(waves.get(4).round).isEqualTo(5);
    }

    @Test
    void wavesAreGenerated_roundsInGame() {
        givenBossWave();

        whenGameIsStarted();
        whenAllCreepsAreSpawned();

        List<Wave> waves = new ArrayList<>(waveGateway.getWaves());
        assertThat(waves.get(0).round).isEqualTo(2);
        assertThat(waves.get(1).round).isEqualTo(3);
        assertThat(waves.get(2).round).isEqualTo(4);
        assertThat(waves.get(3).round).isEqualTo(5);
        assertThat(waves.get(4).round).isEqualTo(6);
    }

    @Test
    void boss() {
        givenBossWave();

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
        givenBossWave();

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
        givenBossWave();

        whenGameIsStarted();
        Creep creep = getCreep(0);
        creep.onDeath.add(deathCreep::set);
        creep.setHealth(0.0f);

        assertThat(deathCreep.get()).isSameAs(creep);
    }

    @Test
    void allCreepsOfWaveAreKilled() {
        givenBossWave();

        whenGameIsStarted();
        whenCreepIsKilled(getCreep(0));
        whenGameIsUpdated(Balancing.WAVE_COUNTDOWN_SECONDS);
        whenGameIsUpdated();

        assertThat(unitGateway.getUnits()).isNotEmpty();
    }

    @Test
    void notAllCreepsOfWaveAreKilled() {
        Wave wave = new Wave();
        wave.creepCount = 2;
        waveGateway.addWave(wave);

        whenGameIsStarted();
        whenCreepIsKilled(getCreep(0));
        whenGameIsUpdated(Balancing.WAVE_COUNTDOWN_SECONDS);
        whenGameIsUpdated();

        assertThat(unitGateway.getUnits()).hasSize(1);
    }

    @Test
    void creepReachesTarget() {
        givenBossWave();
        whenGameIsStarted();
        Creep creep = getCreep(0);
        creep.setPath(new Path(0.0f, 0.0f, 0.0f, 1.0f));

        creep.simulate(1.0f);

        assertThat(unitGateway.getUnits()).isEmpty();
    }

    @Test
    void allCreepsReachTarget() {
        givenBossWave();
        whenGameIsStarted();
        Creep creep = getCreep(0);
        creep.setPath(new Path(0.0f, 0.0f, 0.0f, 1.0f));

        creep.simulate(1.0f);
        whenGameIsUpdated(Balancing.WAVE_COUNTDOWN_SECONDS);
        whenGameIsUpdated();

        assertThat(unitGateway.getUnits()).isNotEmpty();
    }

    @Test
    void armor_round1() {
        givenBossWave();

        whenAllCreepsAreSpawned();

        assertThat(getCreep(0).getArmor()).isEqualTo(1);
    }

    @Test
    void armor_round10() {
        waveGateway.setCurrentRound(9);
        givenBossWave();

        whenAllCreepsAreSpawned();

        assertThat(getCreep(0).getArmor()).isEqualTo(10);
    }

    @Test
    void gold_boss() {
        givenBossWave();

        whenAllCreepsAreSpawned();

        assertThat(getCreep(0).getGold()).isEqualTo(51);
    }

    @Test
    void gold_boss_higherLevel() {
        waveGateway.setCurrentRound(9);
        givenBossWave();

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

    @Test
    void health_boss() {
        givenBossWave();

        whenAllCreepsAreSpawned();

        assertThat(getCreep(0).getHealth()).isEqualTo(256);
        assertThat(getCreep(0).getMaxHealth()).isEqualTo(256);
    }

    @Test
    void health_boss_difficulty_easy() {
        waveGateway.setCurrentRound(5);
        difficultyGateway.setDifficulty(Difficulty.Easy);
        givenBossWave();

        whenAllCreepsAreSpawned();

        assertThat(getCreep(0).getHealth()).isEqualTo(607);
    }

    @Test
    void health_boss_difficulty_hard() {
        waveGateway.setCurrentRound(5);
        difficultyGateway.setDifficulty(Difficulty.Hard);
        givenBossWave();

        whenAllCreepsAreSpawned();

        assertThat(getCreep(0).getHealth()).isEqualTo(620);
    }

    @Test
    void health_distributed() {
        Wave wave = new Wave();
        wave.creepCount = 2;
        waveGateway.addWave(wave);

        whenAllCreepsAreSpawned();

        assertThat(getCreep(0).getHealth()).isEqualTo(128);
        assertThat(getCreep(1).getHealth()).isEqualTo(128);
    }

    @Test
    void health_modifier_atLeastOnePerCreep() {
        Wave wave = new Wave();
        wave.creepCount = 2;
        wave.healthMultiplier = 0.001f;
        waveGateway.addWave(wave);

        whenAllCreepsAreSpawned();

        assertThat(getCreep(0).getHealth()).isEqualTo(1);
        assertThat(getCreep(1).getHealth()).isEqualTo(1);
    }

    private Creep getCreep(int index) {
        return (Creep) unitGateway.getUnits().get(index);
    }

    private void givenBossWave() {
        Wave wave = new Wave();
        wave.creepCount = 1;
        waveGateway.addWave(wave);
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

    private void whenGameIsUpdated(float dt) {
        simulationListeners.onUpdate.dispatch(dt);
    }

    private void whenGameIsUpdated() {
        whenGameIsUpdated(1.0f);
    }

    private void whenCreepIsKilled(Creep creep) {
        creep.setHealth(0.0f);
        creep.simulate(Creep.DEATH_TIME);
        assertThat(unitGateway.getUnits()).doesNotContain(creep);
    }
}