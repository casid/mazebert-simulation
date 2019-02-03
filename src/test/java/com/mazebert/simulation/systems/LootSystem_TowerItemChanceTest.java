package com.mazebert.simulation.systems;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

strictfp class LootSystem_TowerItemChanceTest {
    LootSystem lootSystem = new LootSystem();

    @Test
    void one() {
        assertThat(lootSystem.getTowerItemChanceFactor(1.0f)).isEqualTo(1.0f);
    }

    @Test
    void negative() {
        assertThat(lootSystem.getTowerItemChanceFactor(-0.2f)).isEqualTo(0.0f);
    }

    @Test
    void lessThanOne() {
        assertThat(lootSystem.getTowerItemChanceFactor(0.8f)).isEqualTo(0.88888896f);
    }

    @Test
    void boosted() {
        assertThat(lootSystem.getTowerItemChanceFactor(1.1f)).isEqualTo(1.0022625f);
    }

    @Test
    void boosted2() {
        assertThat(lootSystem.getTowerItemChanceFactor(1.5f)).isEqualTo(1.0384616f);
    }

    @Test
    void boosted3() {
        assertThat(lootSystem.getTowerItemChanceFactor(2.0f)).isEqualTo(1.1f);
    }
}