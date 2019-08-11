package com.mazebert.simulation.gateways;

import com.mazebert.simulation.*;
import com.mazebert.simulation.plugins.random.RandomPluginTrainer;
import com.mazebert.simulation.systems.GameSystem;
import com.mazebert.simulation.units.creeps.CreepModifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class WaveGatewayTest extends SimTest {
    static final float FAST_ROLL = 0.0f;
    static final float SLOW_ROLL = 0.15f;
    static final float WISDOM_ROLL = 0.25f;
    static final float UNION_ROLL = 0.75f;

    static final float BOSS_ROLL = 0.6f;

    RandomPluginTrainer randomPluginTrainer = new RandomPluginTrainer();
    PlayerGatewayTrainer playerGatewayTrainer = new PlayerGatewayTrainer();

    int round;

    Wave wave;

    @BeforeEach
    void setUp() {
        randomPlugin = randomPluginTrainer;
        playerGateway = playerGatewayTrainer;
        waveGateway = new WaveGateway();
        gameSystem = new GameSystem();
        season = true;
    }

    @Test
    void waveGeneration_normal() {
        randomPluginTrainer.givenFloatAbs(0.0f);

        whenWaveIsGenerated();

        assertThat(wave.type).isEqualTo(WaveType.Normal);
        assertThat(wave.creepCount).isEqualTo(10);
        assertThat(wave.minSecondsToNextCreep).isEqualTo(1.0f);
        assertThat(wave.maxSecondsToNextCreep).isEqualTo(1.6f);
    }

    @Test
    void waveGeneration_mass() {
        randomPluginTrainer.givenFloatAbs(0.26f);

        whenWaveIsGenerated();

        assertThat(wave.type).isEqualTo(WaveType.Mass);
        assertThat(wave.creepCount).isEqualTo(20);
        assertThat(wave.minSecondsToNextCreep).isEqualTo(0.2f);
        assertThat(wave.maxSecondsToNextCreep).isEqualTo(0.6f);
    }

    @Test
    void waveGeneration_boss() {
        randomPluginTrainer.givenFloatAbs(0.51f);

        whenWaveIsGenerated();

        assertThat(wave.type).isEqualTo(WaveType.Boss);
        assertThat(wave.creepCount).isEqualTo(1);
    }

    @Test
    void waveGeneration_air() {
        randomPluginTrainer.givenFloatAbs(0.76f);

        whenWaveIsGenerated();

        assertThat(wave.type).isEqualTo(WaveType.Air);
        assertThat(wave.creepCount).isEqualTo(5);
        assertThat(wave.minSecondsToNextCreep).isEqualTo(1.6f);
        assertThat(wave.maxSecondsToNextCreep).isEqualTo(3.2f);
    }

    @Test
    void waveGeneration_armorType_ber() {
        randomPluginTrainer.givenFloatAbs(0.0f, 0.0f);
        whenWaveIsGenerated();
        assertThat(wave.armorType).isEqualTo(ArmorType.Ber);
    }

    @Test
    void waveGeneration_armorType_fal() {
        randomPluginTrainer.givenFloatAbs(0.0f, 0.4f);
        whenWaveIsGenerated();
        assertThat(wave.armorType).isEqualTo(ArmorType.Fal);
    }

    @Test
    void waveGeneration_armorType_vex() {
        randomPluginTrainer.givenFloatAbs(0.0f, 0.8f);
        whenWaveIsGenerated();
        assertThat(wave.armorType).isEqualTo(ArmorType.Vex);
    }

    @Test
    void waveGeneration_modifiers() {
        round = 101;
        randomPluginTrainer.givenFloatAbs(0.0f, 0.0f, 0.0f, 0.0f);
        whenWaveIsGenerated();
        assertThat(wave.creepModifier1).isEqualTo(null);
        assertThat(wave.creepModifier2).isEqualTo(null);
    }

    @Test
    void waveGeneration_modifiers_fast() {
        round = 101;
        randomPluginTrainer.givenFloatAbs(0.0f, 0.0f, 0.0f, 0.31f, FAST_ROLL);
        whenWaveIsGenerated();
        assertThat(wave.creepModifier1).isEqualTo(CreepModifier.Fast);
        assertThat(wave.creepModifier2).isEqualTo(null);
    }

    @Test
    void waveGeneration_modifiers_fast_notPossible() {
        round = 1;
        randomPluginTrainer.givenFloatAbs(0.0f, 0.0f, 0.0f, 0.31f, FAST_ROLL);
        whenWaveIsGenerated();
        assertThat(wave.creepModifier1).isEqualTo(null);
        assertThat(wave.creepModifier2).isEqualTo(null);
    }

    @Test
    void waveGeneration_modifiers_slow_notPossible() {
        round = 1;
        randomPluginTrainer.givenFloatAbs(0.0f, 0.0f, 0.0f, 0.31f, WISDOM_ROLL, 0.31f, SLOW_ROLL);
        whenWaveIsGenerated();
        assertThat(wave.creepModifier1).isEqualTo(CreepModifier.Wisdom);
        assertThat(wave.creepModifier2).isEqualTo(null);
    }

    @Test
    void waveGeneration_modifiers_fast_wisdom() {
        round = 101;
        randomPluginTrainer.givenFloatAbs(0.0f, 0.0f, 0.0f, 0.31f, FAST_ROLL, 0.31f, WISDOM_ROLL);
        whenWaveIsGenerated();
        assertThat(wave.creepModifier1).isEqualTo(CreepModifier.Fast);
        assertThat(wave.creepModifier2).isEqualTo(CreepModifier.Wisdom);
    }

    @Test
    void waveGeneration_modifiers_fast_slow_notPossible() {
        round = 101;
        randomPluginTrainer.givenFloatAbs(0.0f, 0.0f, 0.0f, 0.31f, FAST_ROLL, 0.31f, SLOW_ROLL);
        whenWaveIsGenerated();
        assertThat(wave.creepModifier1).isEqualTo(CreepModifier.Fast);
        assertThat(wave.creepModifier2).isEqualTo(null);
    }

    @Test
    void waveGeneration_modifiers_same_same_notPossible() {
        round = 101;
        randomPluginTrainer.givenFloatAbs(0.0f, 0.0f, 0.0f, 0.31f, WISDOM_ROLL, 0.31f, WISDOM_ROLL);
        whenWaveIsGenerated();
        assertThat(wave.creepModifier1).isEqualTo(CreepModifier.Wisdom);
        assertThat(wave.creepModifier2).isEqualTo(null);
    }

    @Test
    void waveGeneration_modifiers_unionBoss_notPossible() {
        round = 101;
        randomPluginTrainer.givenFloatAbs(BOSS_ROLL, 0.0f, 0.0f, 0.31f, UNION_ROLL, 0.0f);

        whenWaveIsGenerated();

        assertThat(wave.type).isEqualTo(WaveType.Boss);
        assertThat(wave.creepModifier1).isEqualTo(null);
        assertThat(wave.creepModifier2).isEqualTo(null);
    }

    @Test
    void waveGeneration_modifiers_unionBoss_possibleForMultiPlayer() {
        round = 101;
        randomPluginTrainer.givenFloatAbs(BOSS_ROLL, 0.0f, 0.0f, 0.31f, UNION_ROLL, 0.0f);
        playerGatewayTrainer.givenPlayerCount(2);

        whenWaveIsGenerated();

        assertThat(wave.type).isEqualTo(WaveType.Boss);
        assertThat(wave.creepModifier1).isEqualTo(CreepModifier.Union);
        assertThat(wave.creepModifier2).isEqualTo(null);
    }

    @Test
    void waveGeneration_challenge() {
        round = 7;

        whenWaveIsGenerated();

        assertThat(wave.type).isEqualTo(WaveType.Challenge);
        assertThat(wave.creepCount).isEqualTo(1);
        assertThat(wave.armorType).isEqualTo(ArmorType.Zod);
    }

    @Test
    void waveGeneration_massChallenge() {
        round = 14;

        whenWaveIsGenerated();

        assertThat(wave.type).isEqualTo(WaveType.MassChallenge);
        assertThat(wave.creepCount).isEqualTo(20);
        assertThat(wave.armorType).isEqualTo(ArmorType.Zod);
    }

    @Test
    void waveGeneration_horseman() {
        round = 50;

        whenWaveIsGenerated();

        assertThat(wave.type).isEqualTo(WaveType.Horseman);
        assertThat(wave.creepCount).isEqualTo(1);
        assertThat(wave.armorType).isEqualTo(ArmorType.Zod);
    }

    private void whenWaveIsGenerated() {
        wave = waveGateway.generateWave(randomPlugin, round);
    }
}