package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.SimTest;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

class PotionTypeTest extends SimTest {
    @Test
    void uniqueIds() {
        Map<Integer, PotionType> ids = new HashMap<>();
        for (PotionType type : PotionType.values()) {
            PotionType existing = ids.get(type.id);
            if (existing != null) {
                fail("Please choose a unique id for potion " + type + " (the id " + existing.id + " is already taken by " + existing + ")");
            }
            ids.put(type.id, type);
        }
    }

    @Test
    void allCardsAreAvailable() {
        season = true;
        assertThat(PotionType.getValues()).containsExactly(PotionType.values());
    }
}