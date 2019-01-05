package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.units.items.ItemTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

strictfp class CardDustLevelTest extends ItemTest {
    @Test
    void canBeConsumedMultipleTimes() {
        whenPotionIsConsumed(PotionType.CardDustLevel);
        whenPotionIsConsumed(PotionType.CardDustLevel);

        assertThat(tower.getLevel()).isEqualTo(2);
    }
}