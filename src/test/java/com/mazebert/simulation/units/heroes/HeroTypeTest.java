package com.mazebert.simulation.units.heroes;

import com.mazebert.simulation.SimTest;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

class HeroTypeTest extends SimTest {
    @Test
    void uniqueIds() {
        Map<Integer, HeroType> ids = new HashMap<>();
        for (HeroType type : HeroType.values()) {
            HeroType existing = ids.get(type.id);
            if (existing != null) {
                fail("Please choose a unique id for hero " + type + " (the id " + existing.id + " is already taken by " + existing + ")");
            }
            ids.put(type.id, type);
        }
    }

    @Test
    void allCardsAreAvailable() {
        season = true;
        assertThat(HeroType.getValues()).containsExactly(HeroType.values());
    }
}