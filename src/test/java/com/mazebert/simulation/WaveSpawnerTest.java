package com.mazebert.simulation;

import com.mazebert.simulation.gateways.*;
import com.mazebert.simulation.maps.BloodMoor;
import com.mazebert.simulation.plugins.FormatPlugin;
import com.mazebert.simulation.plugins.random.RandomPluginTrainer;
import com.mazebert.simulation.systems.ExperienceSystem;
import com.mazebert.simulation.systems.GameSystem;
import com.mazebert.simulation.systems.LootSystem;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.creeps.CreepModifier;
import com.mazebert.simulation.units.creeps.CreepState;
import com.mazebert.simulation.units.creeps.CreepType;
import com.mazebert.simulation.units.creeps.effects.ReviveEffect;
import com.mazebert.simulation.units.wizards.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;

public strictfp class WaveSpawnerTest extends SimTest {

    RandomPluginTrainer randomPluginTrainer = new RandomPluginTrainer();

    WaveSpawner waveSpawner;
    Wave wave;

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
        gameSystem = new GameSystem();
        experienceSystem = new ExperienceSystem();

        gameGateway.getGame().map = new BloodMoor();

        waveSpawner = new WaveSpawner();
        waveGateway.setTotalWaves(250);

        wizard = gameSystem.addWizard(1);
        wizard.towerStash.setElements(EnumSet.of(Element.Nature));
    }

    @Test
    void noWaveExists() {
        whenGameIsStarted();
        assertThat(unitGateway.getAmount(Creep.class)).isEqualTo(0);
        assertThat(waveGateway.getCurrentRound()).isEqualTo(0);
    }

    @Test
    void waveIsRemoved() {
        wave = new Wave();
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
        wave = new Wave();
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
        wave = new Wave();
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
        wave = new Wave();
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
    void creepReachesTarget_challenge() {
        givenWave(WaveType.Challenge);
        whenGameIsStarted();
        Creep creep = getCreep(0);
        creep.setPath(new Path(0.0f, 0.0f, 0.0f, 1.0f));

        creep.simulate(1.0f);

        assertThat(wizard.health).isEqualTo(1.0f);
    }

    @Test
    void creepReachesTarget_massChallenge() {
        givenWave(WaveType.MassChallenge);
        whenGameIsStarted();
        Creep creep = getCreep(0);
        creep.setPath(new Path(0.0f, 0.0f, 0.0f, 1.0f));

        creep.simulate(1.0f);

        assertThat(wizard.health).isEqualTo(1.0f);
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
        givenBossWave();
        wave.round = 10;

        whenAllCreepsAreSpawned();

        assertThat(getCreep(0).getArmor()).isEqualTo(10);
    }

    @Test
    void modifier_rich() {
        wave = new Wave();
        wave.round = 1;
        wave.creepCount = 1;
        wave.creepType = CreepType.Orc;
        wave.creepModifier1 = CreepModifier.Rich;
        waveGateway.addWave(wave);

        whenAllCreepsAreSpawned();

        assertThat(getCreep(0).getGold()).isEqualTo(81);
    }

    @Test
    void modifier_fast() {
        wave = new Wave();
        wave.round = 1;
        wave.creepCount = 1;
        wave.creepType = CreepType.Orc;
        wave.creepModifier1 = CreepModifier.Fast;
        waveGateway.addWave(wave);

        whenAllCreepsAreSpawned();

        assertThat(getCreep(0).getSpeedModifier()).isEqualTo(1.5f);
        assertThat(getCreep(0).getMaxHealth()).isEqualTo(170.66666666666666);
        assertThat(getCreep(0).getHealth()).isEqualTo(170.66666666666666);
    }

    @Test
    void modifier_slow() {
        wave = new Wave();
        wave.round = 1;
        wave.creepCount = 1;
        wave.creepType = CreepType.Orc;
        wave.creepModifier1 = CreepModifier.Slow;
        waveGateway.addWave(wave);

        whenAllCreepsAreSpawned();

        assertThat(getCreep(0).getSpeedModifier()).isEqualTo(0.6666667f);
        assertThat(getCreep(0).getMaxHealth()).isEqualTo(384);
        assertThat(getCreep(0).getHealth()).isEqualTo(384);
    }

    @Test
    void modifier_armor() {
        wave = new Wave();
        wave.round = 1;
        wave.creepCount = 1;
        wave.creepType = CreepType.Orc;
        wave.creepModifier1 = CreepModifier.Armor;
        waveGateway.addWave(wave);

        whenAllCreepsAreSpawned();

        assertThat(getCreep(0).getArmor()).isEqualTo(31);
    }

    @Test
    void modifier_secondChance() {
        wave = new Wave();
        wave.round = 1;
        wave.creepCount = 1;
        wave.creepType = CreepType.Orc;
        wave.creepModifier1 = CreepModifier.Revive;
        waveGateway.addWave(wave);

        whenAllCreepsAreSpawned();

        assertThat(getCreep(0).getHealth()).isEqualTo(128);
        assertThat(getCreep(0).getAbility(ReviveEffect.class)).isNotNull();
    }

    @Test
    void modifier_rich_wisdom() {
        wave = new Wave();
        wave.round = 1;
        wave.creepCount = 1;
        wave.creepType = CreepType.Orc;
        wave.creepModifier1 = CreepModifier.Rich;
        wave.creepModifier2 = CreepModifier.Wisdom;
        waveGateway.addWave(wave);

        whenAllCreepsAreSpawned();

        assertThat(getCreep(0).getGold()).isEqualTo(81);
        assertThat(getCreep(0).getExperienceModifier()).isEqualTo(1.6f);
    }

    @Test
    void gold_boss() {
        givenBossWave();

        whenAllCreepsAreSpawned();

        assertThat(getCreep(0).getGold()).isEqualTo(51);
    }

    @Test
    void gold_boss_higherLevel() {
        givenBossWave();
        wave.round = 10;

        whenAllCreepsAreSpawned();

        assertThat(getCreep(0).getGold()).isEqualTo(57);
    }

    @Test
    void gold_distributed() {
        wave = new Wave();
        wave.round = 1;
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
        difficultyGateway.setDifficulty(Difficulty.Easy);
        givenBossWave();
        wave.round = 6;

        whenAllCreepsAreSpawned();

        assertThat(getCreep(0).getHealth()).isEqualTo(607);
    }

    @Test
    void health_boss_difficulty_hard() {
        difficultyGateway.setDifficulty(Difficulty.Hard);
        givenBossWave();
        wave.round = 6;

        whenAllCreepsAreSpawned();

        assertThat(getCreep(0).getHealth()).isEqualTo(620);
    }

    @Test
    void health_distributed() {
        wave = new Wave();
        wave.round = 1;
        wave.creepCount = 2;
        waveGateway.addWave(wave);

        whenAllCreepsAreSpawned();

        assertThat(getCreep(0).getHealth()).isEqualTo(128);
        assertThat(getCreep(1).getHealth()).isEqualTo(128);
    }

    @Test
    void health_modifier_atLeastOnePerCreep() {
        wave = new Wave();
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
    void experience_round11() {
        givenBossWave();
        wave.round = 11;

        whenAllCreepsAreSpawned();

        assertThat(getCreep(0).getExperience()).isEqualTo(23.558979f);
    }

    @Test
    void orc() {
        wave = new Wave();
        wave.creepCount = 1;
        wave.creepType = CreepType.Orc;
        waveGateway.addWave(wave);

        whenGameIsStarted();

        assertThat(getCreep(0).getType()).isEqualTo(CreepType.Orc);
    }

    @Test
    void rat() {
        wave = new Wave();
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
        assertThat(wizard.towerStash.size()).isEqualTo(1);
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

    @Test
    void gameLost() {
        AtomicBoolean gameLost = new AtomicBoolean();
        simulationListeners.onGameLost.add(() -> gameLost.set(true));
        gameGateway.getGame().health = 0.01f;
        givenBossWave();
        whenAllCreepsAreSpawned();
        Creep creep = getCreep(0);
        creep.setPath(new Path(0.0f, 0.0f, 0.0f, 1.0f));

        creep.simulate(1.0f);

        assertThat(gameGateway.getGame().health).isEqualTo(0.0f);
        assertThat(gameLost.get()).isTrue();
        assertThat(waveCountDown).isNull(); // No new wave to be sent

    }

    @Test
    void gameWon_bonusRound() {
        AtomicBoolean gameWon = new AtomicBoolean();
        simulationListeners.onGameWon.add(() -> gameWon.set(true));
        whenBonusRoundIsReached();

        assertThat(gameWon.get()).isTrue();
        assertThat(bonusRoundCountDown).isNotNull();
        assertThat(gameGateway.getGame().bonusRound).isTrue();
        assertThat(gameGateway.getGame().bonusRoundSeconds).isEqualTo(0);
    }

    @Test
    void gameWon_bonusRound_evenIfTreasureGoblinStillAlive() {
        waveGateway.setTotalWaves(10);
        waveGateway.setCurrentRound(9);
        givenBossWave();
        whenAllCreepsAreSpawned();
        waveSpawner.spawnTreasureGoblins(wizard, 5);
        simulationListeners.onUpdate.dispatch(1.0f);

        whenCreepIsKilled(getCreep(0));

        assertThat(gameGateway.getGame().bonusRound).isTrue();
    }

    @Test
    void bonusRound_creepsAreSpawned() {
        whenBonusRoundIsReached();
        bonusRoundCountDown.onUpdate(bonusRoundCountDown.getRemainingSeconds());
        assertThat(bonusRoundCountDown).isNull();

        simulationListeners.onUpdate.dispatch(1.0f);

        Creep creep = getCreep(0);
        assertThat(creep.getArmor()).isEqualTo(10);
        assertThat(creep.getMaxDrops()).isEqualTo(0); // Bonus round creeps drop no loot
        assertThat(creep.getMinDrops()).isEqualTo(0); // Bonus round creeps drop no loot
        assertThat(creep.getGold()).isEqualTo(0); // Bonus round creeps drop no gold
    }

    @Test
    void bonusRound_seconds() {
        whenBonusRoundIsReached();
        bonusRoundCountDown.onUpdate(bonusRoundCountDown.getRemainingSeconds());

        assertThat(gameGateway.getGame().bonusRoundSeconds).isEqualTo(0);
        simulationListeners.onUpdate.dispatch(1.0f);
        assertThat(gameGateway.getGame().bonusRoundSeconds).isEqualTo(1);
    }

    @Test
    void bonusRound_experience() {
        whenBonusRoundIsReached();
        bonusRoundCountDown.onUpdate(bonusRoundCountDown.getRemainingSeconds());

        assertThat(wizard.experience).isEqualTo(1);
        simulationListeners.onUpdate.dispatch(30);
        assertThat(wizard.experience).isEqualTo(83);
    }

    @Test
    void bonusRound_spawnsContinuously() {
        whenBonusRoundIsReached();
        bonusRoundCountDown.onUpdate(bonusRoundCountDown.getRemainingSeconds());
        for (int i = 0; i < 50; ++i) {
            simulationListeners.onUpdate.dispatch(1.0f);
        }

        assertThat(unitGateway.getAmount(Creep.class)).isEqualTo(50); // caps at 50 creeps max
    }

    @Test
    void bonusRound_finished() {
        AtomicBoolean gameLost = new AtomicBoolean();
        simulationListeners.onGameLost.add(() -> gameLost.set(true));
        AtomicBoolean finished = new AtomicBoolean();
        simulationListeners.onBonusRoundFinished.add(() -> finished.set(true));
        gameGateway.getGame().health = 0.01f; // not much health left
        whenBonusRoundIsReached();
        bonusRoundCountDown.onUpdate(bonusRoundCountDown.getRemainingSeconds());
        simulationListeners.onUpdate.dispatch(1.0f);
        Creep creep = getCreep(0);
        creep.setPath(new Path(0.0f, 0.0f, 0.0f, 1.0f));

        creep.simulate(1.0f); // creep reaches base, causing bonus round to finish

        assertThat(gameLost.get()).isFalse();
        assertThat(finished.get()).isTrue();
        assertThat(creep.isDead()).isTrue();
        assertThat(getCreep(0)).isSameAs(creep); // Must not be removed for death animation!
    }

    @Test
    void goblinReachesBase() {
        waveSpawner.spawnTreasureGoblins(wizard, 1);
        simulationListeners.onUpdate.dispatch(1.0f);

        Creep goblin = unitGateway.findUnit(Creep.class, wizard.getPlayerId());
        goblin.simulate(1000);

        assertThat(wizard.health).isEqualTo(0.95f);
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
        wave = new Wave();
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

    private void whenBonusRoundIsReached() {
        waveGateway.setTotalWaves(10);
        waveGateway.setCurrentRound(9);
        givenBossWave();

        whenAllCreepsAreSpawned();
        whenCreepIsKilled(getCreep(0));
    }
}