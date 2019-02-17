package com.mazebert.simulation.units.quests;

import com.mazebert.simulation.units.items.ItemTest;
import com.mazebert.simulation.units.potions.PotionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DrinkAllPotionsQuestTest extends ItemTest {

    private DrinkAllPotionsQuest quest;

    @BeforeEach
    void setUp() {
        quest = new DrinkAllPotionsQuest();
        wizard.addAbility(quest);
    }

    @Test
    void drinksAreAdded() {
        assertThat(wizard.potionStash.get(PotionType.DrinkAll).amount).isEqualTo(50);
    }

    @Test
    void allDrinksConsumed() {
        whenAllPotionAreConsumed(PotionType.DrinkAll);
        assertThat(quest.isComplete()).isTrue();
    }

    @Test
    void drinkAllButNoneExist() {
        whenAllPotionAreConsumed(PotionType.Tears);
        assertThat(quest.isComplete()).isFalse();
    }
}