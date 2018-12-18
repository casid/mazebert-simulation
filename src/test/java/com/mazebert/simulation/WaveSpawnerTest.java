package com.mazebert.simulation;

import com.mazebert.simulation.gateways.*;
import com.mazebert.simulation.maps.BloodMoor;
import com.mazebert.simulation.plugins.FormatPlugin;
import com.mazebert.simulation.plugins.random.RandomPluginTrainer;
import com.mazebert.simulation.systems.ExperienceSystem;
import com.mazebert.simulation.systems.LootSystem;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.creeps.CreepState;
import com.mazebert.simulation.units.creeps.CreepType;
import com.mazebert.simulation.units.wizards.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;

public class WaveSpawnerTest extends SimTest {

    RandomPluginTrainer randomPluginTrainer = new RandomPluginTrainer();

    WaveSpawner waveSpawner;

    Wizard wizard;

    boolean waveFinished;

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();
        simulationListeners.onWaveFinished.add(wave -> waveFinished = true);

        formatPlugin = new FormatPlugin();

        unitGateway = new UnitGateway();
        waveGateway = new WaveGateway();
        playerGateway = new PlayerGatewayTrainer();
        randomPlugin = randomPluginTrainer;
        difficultyGateway = new DifficultyGateway();
        gameGateway = new GameGateway();
        lootSystem = new LootSystem();
        experienceSystem = new ExperienceSystem();

        gameGateway.getGame().map = new BloodMoor();

        waveSpawner = new WaveSpawner();
        waveGateway.setTotalWaves(250);

        wizard = new Wizard();
        wizard.playerId = 1;
        unitGateway.addUnit(wizard);
    }

    @Test
    void noWaveExists() {
        whenGameIsStarted();
        assertThat(unitGateway.getAmount(Creep.class)).isEqualTo(0);
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

        assertThat(waveGateway.getWaves()).hasSize(3);
    }

    @Test
    void wavesAreGenerated_notMoreThanMax() {
        waveGateway.setTotalWaves(3);
        givenBossWave();

        whenGameIsStarted();

        assertThat(waveGateway.getWaves()).hasSize(2);
    }

    @Test
    void wavesAreGenerated_round() {
        givenBossWave();

        whenGameIsStarted();

        List<Wave> waves = new ArrayList<>(waveGateway.getWaves());
        assertThat(waves.get(0).round).isEqualTo(1);
        assertThat(waves.get(1).round).isEqualTo(2);
        assertThat(waves.get(2).round).isEqualTo(3);
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
    }

    @Test
    void boss() {
        givenBossWave();

        whenGameIsStarted();

        assertThat(unitGateway.getAmount(Creep.class)).isEqualTo(1);
    }

    @Test
    void creeps() {
        Wave wave = new Wave();
        wave.creepCount = 2;
        wave.minSecondsToNextCreep = 1.0f;
        wave.maxSecondsToNextCreep = 1.0f;
        waveGateway.addWave(wave);

        whenGameIsStarted();
        assertThat(unitGateway.getAmount(Creep.class)).isEqualTo(1);

        whenGameIsUpdated();
        assertThat(unitGateway.getAmount(Creep.class)).isEqualTo(2);
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
        assertThat(unitGateway.getAmount(Creep.class)).isEqualTo(1);

        whenGameIsUpdated();
        assertThat(unitGateway.getAmount(Creep.class)).isEqualTo(1);

        whenGameIsUpdated();
        assertThat(unitGateway.getAmount(Creep.class)).isEqualTo(1);

        whenGameIsUpdated();
        assertThat(unitGateway.getAmount(Creep.class)).isEqualTo(2);
    }

    @Test
    void creepIsKilled() {
        AtomicReference<Unit> removedUnit = new AtomicReference<>(null);
        simulationListeners.onUnitRemoved.add(removedUnit::set);
        givenBossWave();

        whenGameIsStarted();
        assertThat(unitGateway.getAmount(Creep.class)).isEqualTo(1);
        Creep creep = getCreep(0);
        creep.setHealth(0.0f);
        assertThat(unitGateway.getAmount(Creep.class)).isEqualTo(1);
        creep.simulate(1.0f);
        assertThat(unitGateway.getAmount(Creep.class)).isEqualTo(1);
        creep.simulate(1.0f);

        assertThat(creep.getState()).isEqualTo(CreepState.Dead);
        assertThat(unitGateway.getAmount(Creep.class)).isEqualTo(0);
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

        assertThat(waveFinished).isTrue();
        assertThat(unitGateway.getAmount(Creep.class)).isGreaterThan(0);
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

        assertThat(unitGateway.getAmount(Creep.class)).isEqualTo(1);
    }

    @Test
    void creepReachesTarget() {
        givenBossWave();
        whenGameIsStarted();
        Creep creep = getCreep(0);
        creep.setPath(new Path(0.0f, 0.0f, 0.0f, 1.0f));

        creep.simulate(1.0f);

        assertThat(unitGateway.getAmount(Creep.class)).isEqualTo(0);
        assertThat(wizard.health).isEqualTo(0.5f);
    }

    @Test
    void creepReachesTarget_normal() {
        givenNormalWave();
        whenGameIsStarted();
        Creep creep = getCreep(0);
        creep.setPath(new Path(0.0f, 0.0f, 0.0f, 1.0f));

        creep.simulate(1.0f);

        assertThat(unitGateway.getAmount(Creep.class)).isEqualTo(0);
        assertThat(wizard.health).isEqualTo(0.95f);
    }

    @Test
    void creepReachesTarget_mass() {
        givenWave(WaveType.Mass);
        whenGameIsStarted();
        Creep creep = getCreep(0);
        creep.setPath(new Path(0.0f, 0.0f, 0.0f, 1.0f));

        creep.simulate(1.0f);

        assertThat(unitGateway.getAmount(Creep.class)).isEqualTo(0);
        assertThat(wizard.health).isEqualTo(0.975f);
    }

    @Test
    void creepReachesTarget_normal_damaged() {
        givenNormalWave();
        whenGameIsStarted();
        Creep creep = getCreep(0);
        creep.setMaxHealth(100);
        creep.setHealth(80);
        creep.setPath(new Path(0.0f, 0.0f, 0.0f, 1.0f));

        creep.simulate(1.0f);

        assertThat(unitGateway.getAmount(Creep.class)).isEqualTo(0);
        assertThat(wizard.health).isEqualTo(0.96f);
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

        assertThat(unitGateway.getAmount(Creep.class)).isGreaterThan(0);
    }

    @Test
    void nextWaveCalled_firstWaveCleared() {
        givenBossWave();
        givenBossWave();
        givenBossWave();

        whenGameIsStarted();
        whenNextWaveIsCalled();

        whenGameIsUpdated(0.1f);
        whenCreepIsKilled(getCreep(0));
        whenGameIsUpdated(Balancing.WAVE_COUNTDOWN_SECONDS);

        assertThat(waveGateway.getCurrentRound()).isEqualTo(2); // must not advance to next round yet

        whenCreepIsKilled(getCreep(0));
        whenGameIsUpdated(Balancing.WAVE_COUNTDOWN_SECONDS);

        assertThat(waveGateway.getCurrentRound()).isEqualTo(3); // all dead, now we can advance
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
    void drops_boss() {
        givenBossWave();

        whenAllCreepsAreSpawned();

        assertThat(getCreep(0).getMinDrops()).isEqualTo(0);
        assertThat(getCreep(0).getMaxDrops()).isEqualTo(4);
        assertThat(getCreep(0).getDropChance()).isEqualTo(5.0f);
        assertThat(getCreep(0).getMaxItemLevel()).isEqualTo(3);
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

    @Test
    void experience_round1() {
        givenBossWave();

        whenAllCreepsAreSpawned();

        assertThat(getCreep(0).getExperience()).isEqualTo(20.3f);
    }

    @Test
    void experience_round10() {
        waveGateway.setCurrentRound(10);
        givenBossWave();

        whenAllCreepsAreSpawned();

        assertThat(getCreep(0).getExperience()).isEqualTo(23.558979f);
    }

    @Test
    void orc() {
        Wave wave = new Wave();
        wave.creepCount = 1;
        wave.creepType = CreepType.Orc;
        waveGateway.addWave(wave);

        whenGameIsStarted();

        assertThat(getCreep(0).getType()).isEqualTo(CreepType.Orc);
    }

    @Test
    void rat() {
        Wave wave = new Wave();
        wave.creepCount = 1;
        wave.creepType = CreepType.Rat;
        waveGateway.addWave(wave);

        whenGameIsStarted();

        assertThat(getCreep(0).getType()).isEqualTo(CreepType.Rat);
    }

    @Test
    void roundCompleted_allKilled() {
        givenBossWave();

        whenGameIsStarted();
        whenCreepIsKilled(getCreep(0));
        whenGameIsUpdated();

        assertThat(waveFinished).isTrue();
    }

    @Test
    void roundCompleted_allCreepsReachTarget_earlyCall() {
        givenBossWave();
        whenGameIsStarted();
        Creep creep = getCreep(0);
        creep.setPath(new Path(0.0f, 0.0f, 0.0f, 1.0f));

        whenPlayerCallsNextWave();

        creep.simulate(1.0f);
        whenGameIsUpdated();

        assertThat(waveFinished).isTrue();
    }

    @Test
    void roundCompleted() {
        givenBossWave();

        whenGameIsStarted();
        whenCreepIsKilled(getCreep(0));
        whenGameIsUpdated();

        assertThat(waveFinished).isTrue();
        assertThat(wizard.towerStash.size()).isEqualTo(1); // Tower research
        assertThat(wizard.experience).isEqualTo(1);
    }

    @Test
    void roundCompleted_interest() {
        wizard.gold = 250;
        givenBossWave();

        whenGameIsStarted();
        whenCreepIsKilled(getCreep(0));
        whenGameIsUpdated();

        assertThat(wizard.gold).isEqualTo(255);
    }

    private void whenPlayerCallsNextWave() {
        waveSpawner.onWaveStarted();
        whenGameIsUpdated();
    }

    private Creep getCreep(int index) {
        return (Creep) unitGateway.getUnit(1 + index); // first unit is our wizard
    }

    private void givenBossWave() {
        givenWave(WaveType.Boss);
    }

    private void givenNormalWave() {
        givenWave(WaveType.Normal);
    }

    private void givenWave(WaveType waveType) {
        Wave wave = new Wave();
        wave.creepCount = waveType.creepCount;
        wave.type = waveType;
        wave.round = 1;
        waveGateway.addWave(wave);
    }

    private void whenGameIsStarted() {
        simulationListeners.onGameStarted.dispatch();
        simulationListeners.onUpdate.dispatch(0.0f);
    }

    private void whenNextWaveIsCalled() {
        simulationListeners.onWaveStarted.dispatch();
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
        assertThat(unitGateway.hasUnit(creep)).isFalse();
    }
}