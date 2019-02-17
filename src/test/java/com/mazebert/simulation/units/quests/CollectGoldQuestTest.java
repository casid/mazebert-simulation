package com.mazebert.simulation.units.quests;

import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.units.wizards.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CollectGoldQuestTest extends SimTest {
    Wizard wizard;
    CollectGoldQuest quest;

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();

        wizard = new Wizard();
        quest = new CollectGoldQuest();
        wizard.addAbility(quest);
    }

    @Test
    void noGold() {
        assertThat(quest.getCurrentAmount()).isEqualTo(0);
    }

    @Test
    void oneGold() {
        wizard.onGoldChanged.dispatch(wizard, 0, 1);
        assertThat(quest.getCurrentAmount()).isEqualTo(1);
    }

    @Test
    void previousAmountPlusOneGold() {
        quest.setCurrentAmount(100);
        wizard.onGoldChanged.dispatch(wizard, 0, 1);
        assertThat(quest.getCurrentAmount()).isEqualTo(101);
    }

    @Test
    void previousAmountPlusTenGold() {
        quest.setCurrentAmount(100);
        wizard.onGoldChanged.dispatch(wizard, 0, 10);
        assertThat(quest.getCurrentAmount()).isEqualTo(110);
    }
}