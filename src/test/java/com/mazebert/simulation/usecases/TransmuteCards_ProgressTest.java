package com.mazebert.simulation.usecases;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.units.wizards.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.mazebert.simulation.usecases.TransmuteCards.getProgress;
import static org.assertj.core.api.Assertions.assertThat;

public class TransmuteCards_ProgressTest extends SimTest {
    Wizard wizard;

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();
        unitGateway = new UnitGateway();

        wizard = new Wizard();
    }

    @Test
    void common0() {
        wizard.itemStash.transmutedCommons = 0;
        assertThat(getProgress(wizard, wizard.itemStash, Rarity.Common)).isEqualTo(0);
    }

    @Test
    void common2() {
        wizard.itemStash.transmutedCommons = 2;
        assertThat(getProgress(wizard, wizard.itemStash, Rarity.Common)).isEqualTo(0.5f);
    }

    @Test
    void uncommon4() {
        wizard.itemStash.transmutedUncommons = 4;
        assertThat(getProgress(wizard, wizard.itemStash, Rarity.Uncommon)).isEqualTo(1.0f);
    }

    @Test
    void rare3() {
        wizard.itemStash.transmutedRares = 3;
        assertThat(getProgress(wizard, wizard.itemStash, Rarity.Rare)).isEqualTo(0.75f);
    }

    @Test
    void unique1() {
        wizard.transmutedUniques = 1;
        assertThat(getProgress(wizard, wizard.itemStash, Rarity.Unique)).isEqualTo(0.5f);
    }
}