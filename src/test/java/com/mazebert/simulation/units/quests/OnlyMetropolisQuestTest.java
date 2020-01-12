package com.mazebert.simulation.units.quests;

import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.plugins.FormatPlugin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

strictfp class OnlyMetropolisQuestTest extends SimTest {

    @BeforeEach
    void setUp() {
        formatPlugin = new FormatPlugin();
    }

    @Test
    void description() {
        assertThat(new OnlyMetropolisQuest().getDescription()).isEqualTo("Win a game with <c=#868686>Metropolis</c> towers only.");
    }
}