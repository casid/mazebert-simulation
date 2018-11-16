package com.mazebert.simulation.units.towers;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class TowerTypeTest {
    @Test
    void uniqueIds() {
        Map<Integer, TowerType> ids = new HashMap<>();
        for (TowerType type : TowerType.values()) {
            TowerType existing = ids.get(type.id);
            if (existing != null) {
                fail("Please choose a unique id for tower " + type + " (the id " + existing.id + " is already taken by " + existing + ")");
            }
            ids.put(type.id, type);
        }
    }

    @Test
    void allRequiredInfosAreSet() {
        for (TowerType type : TowerType.values()) {
            Tower tower = type.create();

            assertThat(tower.getElement()).describedAs("Tower " + type + " has no element").isNotNull();
            assertThat(tower.getGender()).describedAs("Tower " + type + " has no gender").isNotNull();
            assertThat(tower.getAttackType()).describedAs("Tower " + type + " has no attack type").isNotNull();
        }
    }
}