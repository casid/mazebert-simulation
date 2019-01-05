package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.units.items.ItemTest;
import org.junit.jupiter.api.Test;

strictfp class CardDustLuckTest extends ItemTest {
    @Test
    void canBeConsumedMultipleTimes() {
        whenPotionIsConsumed(PotionType.CardDustLuck);
        whenPotionIsConsumed(PotionType.CardDustLuck);
    }
}