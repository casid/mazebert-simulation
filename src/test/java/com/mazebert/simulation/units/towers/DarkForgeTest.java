package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.Wave;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.plugins.random.RandomPluginTrainer;
import com.mazebert.simulation.units.items.ItemType;
import com.mazebert.simulation.units.wizards.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

strictfp class DarkForgeTest extends SimTest {
    RandomPluginTrainer randomPluginTrainer = new RandomPluginTrainer();

    Wizard wizard;
    DarkForge darkForge;

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();
        unitGateway = new UnitGateway();
        randomPlugin = randomPluginTrainer;

        wizard = new Wizard();
        unitGateway.addUnit(wizard);

        darkForge = new DarkForge();
        darkForge.setWizard(wizard);
        darkForge.setLevel(1);
        unitGateway.addUnit(darkForge);
    }

    @Test
    void darkItemsCanBeForged() {
        randomPluginTrainer.givenFloatAbs(0.0f);
        whenWaveIsFinished();
        thenItemIsCrafted(ItemType.DarkBabySword);
    }

    @Test
    void darkItemsCanBeForged_badLuck() {
        randomPluginTrainer.givenFloatAbs(DarkForgeCraft.CHANCE + 0.001f);
        whenWaveIsFinished();
        thenNoItemIsCrafted();
    }

    private void whenWaveIsFinished() {
        simulationListeners.onWaveFinished.dispatch(new Wave());
    }

    private void thenItemIsCrafted(ItemType item) {
        assertThat(wizard.itemStash.get(item).getAmount()).isEqualTo(1);
    }

    private void thenNoItemIsCrafted() {
        assertThat(wizard.itemStash.size()).isEqualTo(0);
    }
}