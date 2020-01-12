package com.mazebert.simulation.units.quests;

import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.plugins.FormatPlugin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

strictfp class OnlyMetropolisAndLightQuestTest extends SimTest {
    @BeforeEach
    void setUp() {
        formatPlugin = new FormatPlugin();
    }

    @Test
    void description() {
        assertThat(new OnlyMetropolisAndLightQuest().getDescription()).isEqualTo("Win a game with <c=#868686>Metropolis</c> and <c=#ffffff>Light</c> towers only.");
    }
}