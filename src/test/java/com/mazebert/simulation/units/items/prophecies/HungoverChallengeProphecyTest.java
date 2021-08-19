package com.mazebert.simulation.units.items.prophecies;

import com.mazebert.simulation.Wave;
import com.mazebert.simulation.WaveSpawner;
import com.mazebert.simulation.WaveType;
import com.mazebert.simulation.gateways.WaveGateway;
import com.mazebert.simulation.maps.BloodMoor;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.items.ItemType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HungoverChallengeProphecyTest extends ProphecyTest {
    @BeforeEach
    void setUp() {
        gameGateway.getGame().map = new BloodMoor();
        waveGateway = new WaveGateway();
        waveSpawner = new WaveSpawner();
    }

    @Test
    void fulfilled() {
        whenItemIsEquipped(ItemType.HungoverChallengeProphecy);
        givenWave(WaveType.Challenge);

        whenWaveIsSpawned();

        Creep challenge = getCreep();
        assertThat(challenge.isDealsDamage()).isTrue();
        assertThat(challenge.getHealth()).isEqualTo(256.0);
        assertThat(challenge.getMaxHealth()).isEqualTo(512.0);
        assertThat(veleda.getItem(0)).isNull();
    }

    @Test
    void notFulfilledOnMassChallenge() {
        whenItemIsEquipped(ItemType.HungoverChallengeProphecy);
        givenWave(WaveType.MassChallenge);

        whenWaveIsSpawned();

        Creep challenge = getCreep();
        assertThat(challenge.isDealsDamage()).isFalse();
        assertThat(veleda.getItem(0)).isNotNull();
    }

    private Creep getCreep() {
        return unitGateway.findUnitAtIndex(Creep.class, 0);
    }

    private void givenWave(WaveType waveType) {
        Wave wave = new Wave();
        wave.creepCount = waveType.creepCount;
        wave.type = waveType;
        wave.round = waveGateway.getWaves().size() + 1;
        waveGateway.addWave(wave);
    }

    private void whenWaveIsSpawned() {
        waveSpawner.onWaveStarted(0);
        waveSpawner.onUpdate(1.0f);
    }
}