package com.mazebert.simulation;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DifficultyTest {

    @Test
    void lookup() {
        for (Difficulty value : Difficulty.values()) {
            assertThat(Difficulty.forId(value.id)).isSameAs(value);
        }
    }

    @Test
    void lookup_outOfBounds() {
        assertThat(Difficulty.forId(0)).isEqualTo(null);
        assertThat(Difficulty.forId(-1)).isEqualTo(null);
        assertThat(Difficulty.forId(Difficulty.values().length + 1)).isEqualTo(null);
    }
}