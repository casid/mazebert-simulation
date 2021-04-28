package com.mazebert.simulation.units.quests;

import com.mazebert.simulation.Difficulty;
import com.mazebert.simulation.units.items.ItemTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HellVictoryQuestTest extends ItemTest {

    private HellVictoryQuest quest;

    @BeforeEach
    void setUp() {
        quest = new HellVictoryQuest();
        wizard.addAbility(quest);

        difficultyGateway.setDifficulty(Difficulty.Hell);
    }

    @Test
    void won() {
        simulationListeners.onGameWon.dispatch();
        assertThat(quest.isComplete()).isTrue();
    }

    @Test
    void won_normal() {
        difficultyGateway.setDifficulty(Difficulty.Nightmare);
        simulationListeners.onGameWon.dispatch();
        assertThat(quest.isComplete()).isFalse();
    }
}