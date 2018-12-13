package com.mazebert.simulation.gateways;

import com.mazebert.simulation.ArmorType;
import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.Wave;
import com.mazebert.simulation.WaveType;
import com.mazebert.simulation.plugins.random.RandomPluginTrainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class WaveGatewayTest extends SimTest {
    RandomPluginTrainer randomPluginTrainer = new RandomPluginTrainer();

    int round;

    Wave wave;

    @BeforeEach
    void setUp() {
        randomPlugin = randomPluginTrainer;
        waveGateway = new WaveGateway();
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