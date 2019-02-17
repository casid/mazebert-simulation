package com.mazebert.simulation.units.quests;

import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.units.wizards.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BonusSurvivalQuestTest extends SimTest {

    BonusSurvivalQuest quest;

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();

        Wizard wizard = new Wizard();
        quest = new BonusSurvivalQuest();
        wizard.addAbility(quest);
    }

    @Test
    void progress() {
        simulationListeners.onBonusRoundSurvived.dispatch(1);
        simulationListeners.onBonusRoundSurvived.dispatch(2);
        simulationListeners.onBonusRoundSurvived.dispatch(3);
        simulationListeners.onBonusRoundSurvived.dispatch(4);

        assertThat(quest.getCurrentAmount()).isEqualTo(4);
    }

    @Test
    void existingProgress() {
        quest.setCurrentAmount(100);

        simulationListeners.onBonusRoundSurvived.dispatch(1);
        simulationListeners.onBonusRoundSurvived.dispatch(2);
        simulationListeners.onBonusRoundSurvived.dispatch(3);

        assertThat(quest.getCurrentAmount()).isEqualTo(103);
    }

    @Test
    void existingProgress_eventDispatchedTwice() {
        quest.setCurrentAmount(100);

        simulationListeners.onBonusRoundSurvived.dispatch(1);
        simulationListeners.onBonusRoundSurvived.dispatch(2);
        simulationListeners.onBonusRoundSurvived.dispatch(2);
        simulationListeners.onBonusRoundSurvived.dispatch(3);

        assertThat(quest.getCurrentAmount()).isEqualTo(103);
    }

    @Test
    void existingProgress_eventsNotDispatched() {
        quest.setCurrentAmount(100);

        simulationListeners.onBonusRoundSurvived.dispatch(1);
        simulationListeners.onBonusRoundSurvived.dispatch(2);
        simulationListeners.onBonusRoundSurvived.dispatch(2);
        simulationListeners.onBonusRoundSurvived.dispatch(30);

        assertThat(quest.getCurrentAmount()).isEqualTo(130);
    }
}