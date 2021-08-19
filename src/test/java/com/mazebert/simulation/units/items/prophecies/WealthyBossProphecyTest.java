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

public class WealthyBossProphecyTest extends ProphecyTest {

    @BeforeEach
    void setUp() {
        gameGateway.getGame().map = new BloodMoor();
        waveGateway = new WaveGateway();
        waveSpawner = new WaveSpawner();
    }

    @Test
    void fulfilled() {
        whenItemIsEquipped(ItemType.WealthyBossProphecy);
        givenWave(WaveType.Boss);

        whenWaveIsSpawned();

        Creep boss = getCreep();
        assertThat(boss.getGold()).isEqualTo(0);
        assertThat(boss.getMinDrops()).isEqualTo(1);
        assertThat(boss.getMaxDrops()).isEqualTo(5);
        assertThat(veleda.getItem(0)).isNull();
    }

    @Test
    void noProphecy() {
        givenWave(WaveType.Boss);

        whenWaveIsSpawned();

        Creep boss = getCreep();
        assertThat(boss.getGold()).isEqualTo(51);
        assertThat(boss.getMinDrops()).isEqualTo(0);
        assertThat(boss.getMaxDrops()).isEqualTo(4);
    }

    @Test
    void otherCreep() {
        whenItemIsEquipped(ItemType.WealthyBossProphecy);
        givenWave(WaveType.Normal);

        whenWaveIsSpawned();

        Creep creep = getCreep();
        assertThat(creep.getMinDrops()).isEqualTo(0);
        assertThat(veleda.getItem(0).getType()).isEqualTo(ItemType.WealthyBossProphecy);
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
