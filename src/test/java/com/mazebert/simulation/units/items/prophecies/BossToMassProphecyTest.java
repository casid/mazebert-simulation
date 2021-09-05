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

public class BossToMassProphecyTest extends ProphecyTest {

    @BeforeEach
    void setUp() {
        gameGateway.getGame().map = new BloodMoor();
        waveGateway = new WaveGateway();
        waveSpawner = new WaveSpawner();
    }

    @Test
    void fulfilled() {
        whenItemIsEquipped(ItemType.BossToMassProphecy);
        givenWave(WaveType.Boss);

        whenWaveIsSpawned();

        Creep massCreep1 = getCreep(0);
        assertThat(massCreep1.getWave().type).isEqualTo(WaveType.Mass);
        assertThat(massCreep1.getGold()).isEqualTo(3);
        assertThat(massCreep1.getHealth()).isEqualTo(9.216);

        Creep massCreep2 = getCreep(1);
        assertThat(massCreep2.getWave().type).isEqualTo(WaveType.Mass);

        assertThat(veleda.getItem(0)).isNull();
    }

    @Test
    void otherCreep() {
        whenItemIsEquipped(ItemType.BossToMassProphecy);
        givenWave(WaveType.Normal);

        whenWaveIsSpawned();

        Creep creep = getCreep(0);
        assertThat(creep.getWave().type).isEqualTo(WaveType.Normal);
        assertThat(veleda.getItem(0).getType()).isEqualTo(ItemType.BossToMassProphecy);
    }

    private Creep getCreep(int index) {
        return unitGateway.findUnitAtIndex(Creep.class, index);
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
        waveSpawner.onUpdate(1.0f);
        waveSpawner.onUpdate(1.0f);
    }
}
