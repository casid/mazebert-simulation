package com.mazebert.simulation.units.quests;

import com.mazebert.simulation.Difficulty;
import com.mazebert.simulation.units.items.ItemTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HardVictoryQuestTest extends ItemTest {

    private HardVictoryQuest quest;

    @BeforeEach
    void setUp() {
        quest = new HardVictoryQuest();
        wizard.addAbility(quest);

        difficultyGateway.setDifficulty(Difficulty.Hard);
    }

    @Test
    void won() {
        simulationListeners.onGameWon.dispatch();
        assertThat(quest.isComplete()).isTrue();
    }

    @Test
    void won_normal() {
        difficultyGateway.setDifficulty(Difficulty.Normal);
        simulationListeners.onGameWon.dispatch();
        assertThat(quest.isComplete()).isFalse();
    }
}