package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.gateways.GameGateway;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.maps.MapAura;
import com.mazebert.simulation.maps.TestMap;
import com.mazebert.simulation.plugins.random.RandomPluginTrainer;
import com.mazebert.simulation.units.Gender;
import com.mazebert.simulation.units.wizards.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

strictfp class KiwiEggTest extends SimTest {
    RandomPluginTrainer randomPluginTrainer = new RandomPluginTrainer();

    TestMap map;
    Wizard wizard;
    KiwiEgg kiwiEgg;
    Kiwi kiwi;

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();
        unitGateway = new UnitGateway();
        gameGateway = new GameGateway();
        randomPlugin = randomPluginTrainer;

        map = new TestMap(1);
        gameGateway.getGame().map = map;

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
        whenRoundIsStarted();
        thenBreedingProgressIs("1/5");
    }

    @Test
    void hatches_round2() {
        whenRoundIsStarted();
        whenRoundIsStarted();

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

    @Test
    void hatches_aura() {
        map.givenAllTilesHaveAura(MapAura.IncreasedRange);
        whenKiwiHatches();
        assertThat(kiwi.getRange()).isEqualTo(2);
    }

    private void whenKiwiHatches() {
        whenRoundIsStarted();
        whenRoundIsStarted();
        whenRoundIsStarted();
        whenRoundIsStarted();
        whenRoundIsStarted();
    }

    private void whenRoundIsStarted() {
        simulationListeners.onRoundStarted.dispatch(1);
        kiwi = unitGateway.findUnit(Kiwi.class, 0);
    }

    private void thenBreedingProgressIs(String expected) {
        CustomTowerBonus bonus = new CustomTowerBonus();
        kiwiEgg.populateCustomTowerBonus(bonus);
        assertThat(bonus.value).isEqualTo(expected);
    }
}