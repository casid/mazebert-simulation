package com.mazebert.simulation.units.quests;

import com.mazebert.simulation.SimTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class QuestTypeTest extends SimTest {
    @Test
    void allQuestsAreAvailable() {
        season = true;
        assertThat(QuestType.getValues()).containsExactly(QuestType.values());
    }
}