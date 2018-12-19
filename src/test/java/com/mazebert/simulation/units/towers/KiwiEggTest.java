package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.Wave;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.plugins.random.RandomPluginTrainer;
import com.mazebert.simulation.units.Gender;
import com.mazebert.simulation.units.wizards.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

strictfp class KiwiEggTest extends SimTest {
    RandomPluginTrainer randomPluginTrainer = new RandomPluginTrainer();

    Wizard wizard;
    KiwiEgg kiwiEgg;
    Kiwi kiwi;

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();
        unitGateway = new UnitGateway();
        randomPlugin = randomPluginTrainer;

        wizard = new Wizard();
        unitGateway.addUnit(wizard);

        kiwiEgg = new KiwiEgg();
        kiwiEgg.setWizard(wizard);
        unitGateway.addUnit(kiwiEgg);
    }

    @Test
    void hatches_round0() {
        thenBreedingProgressIs("0/5");
    }

    @Test
    void hatches_round1() {
        whenWaveIsFinished();
        thenBreedingProgressIs("1/5");
    }

    @Test
    void hatches_round2() {
        whenWaveIsFinished();
        whenWaveIsFinished();

        thenBreedingProgressIs("2/5");
    }

    @Test
    void hatches() {
        whenKiwiHatches();

        assertThat(unitGateway.hasUnit(kiwiEgg)).isFalse();
        assertThat(kiwi).isNotNull();
    }

    @Test
    void hatches_female() {
        randomPluginTrainer.givenFloatAbs(0.0f);
        whenKiwiHatches();
        assertThat(kiwi.getGender()).isEqualTo(Gender.Female);
    }

    @Test
    void hatches_male() {
        randomPluginTrainer.givenFloatAbs(0.5f);
        whenKiwiHatches();
        assertThat(kiwi.getGender()).isEqualTo(Gender.Male);
    }

    private void whenKiwiHatches() {
        whenWaveIsFinished();
        whenWaveIsFinished();
        whenWaveIsFinished();
        whenWaveIsFinished();
        whenWaveIsFinished();
    }

    private void whenWaveIsFinished() {
        simulationListeners.onWaveFinished.dispatch(new Wave());
        kiwi = unitGateway.findUnit(Kiwi.class, 0);
    }

    private void thenBreedingProgressIs(String expected) {
        CustomTowerBonus bonus = new CustomTowerBonus();
        kiwiEgg.populateCustomTowerBonus(bonus);
        assertThat(bonus.value).isEqualTo(expected);
    }
}