package com.mazebert.simulation.units.items;

import com.mazebert.simulation.SimTest;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.fail;

class ItemTypeTest extends SimTest {

    @Test
    void uniqueIds() {
        Map<Integer, ItemType> ids = new HashMap<>();
        for (ItemType type : ItemType.values()) {
            ItemType existing = ids.get(type.id);
            if (existing != null) {
                fail("Please choose a unique id for item " + type + " (the id " + existing.id + " is already taken by " + existing + ")");
            }
            ids.put(type.id, type);
        }
    }
}