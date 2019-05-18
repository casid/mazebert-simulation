package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.CommandExecutor;
import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.plugins.random.RandomPluginTrainer;
import com.mazebert.simulation.systems.LootSystemTrainer;
import com.mazebert.simulation.units.items.ItemType;
import com.mazebert.simulation.units.wizards.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

strictfp class DarkForge_CraftTest extends SimTest {
    RandomPluginTrainer randomPluginTrainer = new RandomPluginTrainer();

    Wizard wizard;
    DarkForge darkForge;

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();
        unitGateway = new UnitGateway();
        randomPlugin = randomPluginTrainer;
        lootSystem = new LootSystemTrainer();
        commandExecutor = new CommandExecutor();
        commandExecutor.init();

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
        whenRoundIsStarted();
        thenItemIsCrafted(ItemType.DarkBabySword);
    }

    @Test
    void darkItemsCanBeForged_badLuck() {
        randomPluginTrainer.givenFloatAbs(DarkForgeCraft.CHANCE + 0.001f);
        whenRoundIsStarted();
        thenNoItemIsCrafted();
    }

    @Test
    void darkItemsCanBeAutoTransmuted() {
        randomPluginTrainer.givenFloatAbs(0.0f);
        wizard.itemStash.addAutoTransmute(ItemType.DarkBabySword);

        whenRoundIsStarted();

        assertThat(wizard.itemStash.transmutedCommons).isEqualTo(1);
    }

    @Test
    void darkBlade() {
        darkForge.setLevel(66);
        randomPluginTrainer.givenFloatAbs(0.0f, 0.99f);

        whenRoundIsStarted();

        thenItemIsCrafted(ItemType.DarkBlade);
    }

    @Test
    void skullOfDarkness() {
        wizard.foilItems.add(ItemType.SkullOfDarkness);
        darkForge.setLevel(66);
        randomPluginTrainer.givenFloatAbs(0.0f, 0.99f);

        whenRoundIsStarted();

        thenItemIsCrafted(ItemType.SkullOfDarkness);
    }

    @Test
    void darkBlade_onlyOnce() {
        wizard.itemStash.add(ItemType.DarkBlade);
        darkForge.setLevel(66);
        randomPluginTrainer.givenFloatAbs(0.0f, 0.99f);

        whenRoundIsStarted();

        assertThat(wizard.itemStash.get(ItemType.DarkBlade).getAmount()).isEqualTo(1);
    }

    private void whenRoundIsStarted() {
        simulationListeners.onRoundStarted.dispatch(1);
    }

    private void thenItemIsCrafted(ItemType item) {
        assertThat(wizard.itemStash.get(item).getAmount()).isEqualTo(1);
    }

    private void thenNoItemIsCrafted() {
        assertThat(wizard.itemStash.size()).isEqualTo(0);
    }
}