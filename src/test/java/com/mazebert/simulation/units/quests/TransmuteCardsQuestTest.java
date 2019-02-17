package com.mazebert.simulation.units.quests;

import com.mazebert.simulation.units.items.ItemTest;
import com.mazebert.simulation.units.items.ItemType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TransmuteCardsQuestTest extends ItemTest {
    private TransmuteCardsQuest quest;

    @BeforeEach
    void setUp() {
        quest = new TransmuteCardsQuest();
        wizard.addAbility(quest);
    }

    @Test
    void transmuteOne() {
        wizard.itemStash.add(ItemType.BabySword);

        whenCardIsTransmuted(ItemType.BabySword);

        assertThat(quest.getCurrentAmount()).isEqualTo(1);
    }

    @Test
    void transmuteAll() {
        wizard.itemStash.add(ItemType.BabySword);
        wizard.itemStash.add(ItemType.BabySword);
        wizard.itemStash.add(ItemType.BabySword);

        whenAllCardsAreTransmuted(ItemType.BabySword);

        assertThat(quest.getCurrentAmount()).isEqualTo(3);
    }
}