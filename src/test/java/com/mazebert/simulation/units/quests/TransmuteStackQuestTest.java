package com.mazebert.simulation.units.quests;

import com.mazebert.simulation.units.items.ItemTest;
import com.mazebert.simulation.units.items.ItemType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TransmuteStackQuestTest extends ItemTest {
    private TransmuteStackQuest quest;

    @BeforeEach
    void setUp() {
        quest = new TransmuteStackQuest();
        wizard.addAbility(quest);
    }

    @Test
    void notesAreAdded() {
        assertThat(wizard.itemStash.get(ItemType.TransmuteStack).amount).isEqualTo(4);
    }

    @Test
    void transmuteAll() {
        whenAllCardsAreTransmuted(ItemType.TransmuteStack);
        assertThat(quest.getCurrentAmount()).isEqualTo(1);
    }

    @Test
    void transmuteOneByOneIsNotPossible() {
        whenCardIsTransmuted(ItemType.TransmuteStack);
        assertThat(wizard.itemStash.get(ItemType.TransmuteStack).amount).isEqualTo(4);
    }
}