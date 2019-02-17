package com.mazebert.simulation.units.quests;

import com.mazebert.simulation.units.items.ItemTest;
import com.mazebert.simulation.units.items.ItemType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TransmuteUniquesQuestTest extends ItemTest {

    private TransmuteUniquesQuest quest;

    @BeforeEach
    void setUp() {
        quest = new TransmuteUniquesQuest();
        wizard.addAbility(quest);
    }

    @Test
    void notesAreAdded() {
        assertThat(wizard.itemStash.get(ItemType.TransmuteUniques).amount).isEqualTo(2);
    }

    @Test
    void transmuteAll() {
        whenAllCardsAreTransmuted(ItemType.TransmuteUniques);
        assertThat(quest.getCurrentAmount()).isEqualTo(1);
    }

    @Test
    void transmuteOneByOne() {
        whenCardIsTransmuted(ItemType.TransmuteUniques);
        assertThat(quest.getCurrentAmount()).isEqualTo(0);

        whenCardIsTransmuted(ItemType.TransmuteUniques);
        assertThat(quest.getCurrentAmount()).isEqualTo(1);
    }

    @Test
    void transmuteAllOtherItem_noCrash() {
        wizard.itemStash.add(ItemType.BabySword);
        whenAllCardsAreTransmuted(ItemType.BabySword);
        assertThat(quest.getCurrentAmount()).isEqualTo(0);
    }
}