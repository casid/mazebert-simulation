package com.mazebert.simulation.units.items.prophecies;

import com.mazebert.simulation.ArmorType;
import com.mazebert.simulation.Wave;
import com.mazebert.simulation.WaveSpawner;
import com.mazebert.simulation.WaveType;
import com.mazebert.simulation.gateways.WaveGateway;
import com.mazebert.simulation.maps.BloodMoor;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.creeps.CreepType;
import com.mazebert.simulation.units.items.ItemType;
import com.mazebert.simulation.units.quests.NaglfarFailureQuest;
import com.mazebert.simulation.units.quests.NaglfarSuccessQuest;
import com.mazebert.simulation.units.towers.Tower;
import com.mazebert.simulation.units.towers.TowerType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.assertj.core.api.Assertions.assertThat;

public class RagNarRogProphecyTest extends ProphecyTest {

    Wave wave;

    Tower yggdrasil;
    Tower thor;
    Tower loki;
    Tower idun;
    Tower hel;

    @BeforeEach
    void setUp() {
        gameGateway.getGame().map = new BloodMoor();
        waveGateway = new WaveGateway();
        waveSpawner = new WaveSpawner();

        givenWave(WaveType.Challenge);

        wizard.addGold(1_000_000);

        whenItemIsEquipped(ItemType.RagNarRogProphecy);

        yggdrasil = whenTowerIsBuilt(wizard, TowerType.Yggdrasil, 13, 8);
        thor = whenTowerIsBuilt(wizard, TowerType.Thor, 16, 13);
        loki = whenTowerIsBuilt(wizard, TowerType.Loki, 16, 12);
        idun = whenTowerIsBuilt(wizard, TowerType.Idun, 17, 12);
        hel = whenTowerIsBuilt(wizard, TowerType.Hel, 17, 13);

        whenItemIsEquipped(thor, ItemType.BranchOfYggdrasilLight, 1); // First item is Mjoelnir
        whenItemIsEquipped(loki, ItemType.BranchOfYggdrasilMetropolis);
        whenItemIsEquipped(idun, ItemType.BranchOfYggdrasilNature);
        whenItemIsEquipped(hel, ItemType.BranchOfYggdrasilDarkness);
    }

    @Test
    void wrongWaveType() {
        wave.type = WaveType.MassChallenge;
        whenWaveIsSpawned();
        thenProphecyIsNotFulfilled();
    }

    @Test
    void noYggdrasil() {
        whenTowerIsSold(yggdrasil);
        whenWaveIsSpawned();
        thenProphecyIsNotFulfilled();
    }

    @Test
    void noThor() {
        whenTowerIsSold(thor);
        whenWaveIsSpawned();
        thenProphecyIsNotFulfilled();
    }

    @Test
    void noLoki() {
        whenTowerIsSold(loki);
        whenWaveIsSpawned();
        thenProphecyIsNotFulfilled();
    }

    @Test
    void noIdun() {
        whenTowerIsSold(idun);
        whenWaveIsSpawned();
        thenProphecyIsNotFulfilled();
    }

    @Test
    void noHel() {
        whenTowerIsSold(hel);
        whenWaveIsSpawned();
        thenProphecyIsNotFulfilled();
    }

    @Test
    void noLightBranch() {
        whenItemIsUnequipped(thor, 1);
        whenWaveIsSpawned();
        thenProphecyIsNotFulfilled();
    }

    @Test
    void noMetropolisBranch() {
        whenItemIsUnequipped(loki, 0);
        whenWaveIsSpawned();
        thenProphecyIsNotFulfilled();
    }

    @Test
    void noNatureBranch() {
        whenItemIsUnequipped(idun, 0);
        whenWaveIsSpawned();
        thenProphecyIsNotFulfilled();
    }

    @Test
    void noDarknessBranch() {
        whenItemIsUnequipped(hel, 0);
        whenWaveIsSpawned();
        thenProphecyIsNotFulfilled();
    }

    @Test
    void fulfilled() {
        whenWaveIsSpawned();

        Creep naglfar = getCreep(0);
        assertThat(naglfar.getWave().type).isEqualTo(WaveType.Naglfar);
        assertThat(naglfar.getWave().creepType).isEqualTo(CreepType.Naglfar);
        assertThat(naglfar.getWave().armorType).isEqualTo(ArmorType.Zod);
        assertThat(naglfar.getHealth()).isEqualTo(1024.0);
        assertThat(naglfar.getMaxHealth()).isEqualTo(1024.0);
        assertThat(naglfar.getWave().creepCount).isEqualTo(1);
        assertThat(naglfar.isBoss()).isTrue();
        assertThat(veleda.getItem(0)).isNull();
    }

    @Test
    void naglfarLeaked() {
        NaglfarFailureQuest quest = new NaglfarFailureQuest();
        wizard.addAbility(quest);
        AtomicBoolean gameLost = new AtomicBoolean();
        simulationListeners.onGameLost.add(() -> gameLost.set(true));

        whenWaveIsSpawned();
        Creep naglfar = getCreep(0);
        naglfar.simulate(1000);

        assertThat(gameLost.get()).isTrue();
        assertThat(quest.isComplete()).isTrue();
    }

    @Test
    void naglfarKilled() {
        NaglfarSuccessQuest quest = new NaglfarSuccessQuest();
        wizard.addAbility(quest);
        damageSystemTrainer.givenConstantDamage(10000);
        whenWaveIsSpawned();
        Creep naglfar = getCreep(0);

        whenGameUnitsAreSimulated(20);

        assertThat(gameGateway.getGame().isLost()).isFalse();
        assertThat(naglfar.isDead()).isTrue();
        assertThat(quest.isComplete()).isTrue();
    }

    @Test
    void helSoldWhenNaglfarInHelheim() {
        // TODO what happens with Helheim area?
    }

    private Creep getCreep(int index) {
        return unitGateway.findUnitAtIndex(Creep.class, index);
    }

    private void givenWave(WaveType waveType) {
        wave = new Wave();
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

    private void thenProphecyIsNotFulfilled() {
        assertThat(veleda.getItem(0)).isNotNull();
    }
}
