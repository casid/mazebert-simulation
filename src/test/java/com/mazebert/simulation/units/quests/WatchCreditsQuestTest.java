package com.mazebert.simulation.units.quests;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class WatchCreditsQuestTest {
    @Test
    void notSimulated() {
        assertThat(new WatchCreditsQuest().isSimulated()).isFalse();
    }
}