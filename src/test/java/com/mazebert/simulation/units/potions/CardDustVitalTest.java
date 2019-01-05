package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.units.items.ItemTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

strictfp class CardDustVitalTest extends ItemTest {
    @Test
    void canBeConsumedMultipleTimes() {
        whenPotionIsConsumed(PotionType.CardDustVital);
        whenPotionIsConsumed(PotionType.CardDustVital);

        assertThat(gameGateway.getGame().health).isEqualTo(1.4000001f);
    }
}