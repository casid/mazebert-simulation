package com.mazebert.simulation.plugins;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PlayerLevelPluginTest {
    private final PlayerLevelPlugin plugin = new PlayerLevelPlugin();

    @Test
    public void getLevelForExperience() {
        assertThat(plugin.getLevelForExperience(0)).isEqualTo(1);
        assertThat(plugin.getLevelForExperience(500)).isEqualTo(2);
        assertThat(plugin.getLevelForExperience(800)).isEqualTo(3);
        assertThat(plugin.getLevelForExperience(1105)).isEqualTo(4);
        assertThat(plugin.getLevelForExperience(1416)).isEqualTo(5);
        assertThat(plugin.getLevelForExperience(342322)).isEqualTo(69);
        assertThat(plugin.getLevelForExperience(5201039)).isEqualTo(99);
    }

    @Test
    public void getLevelForExperience_caps() {
        assertThat(plugin.getLevelForExperience(-10)).isEqualTo(1);
        assertThat(plugin.getLevelForExperience(5201039000000000000L)).isEqualTo(300);
    }

    @Test
    public void getExperienceForLevel() {
        assertThat(plugin.getExperienceForLevel(1)).isEqualTo(0);
        assertThat(plugin.getExperienceForLevel(2)).isEqualTo(500);
        assertThat(plugin.getExperienceForLevel(3)).isEqualTo(800);
        assertThat(plugin.getExperienceForLevel(4)).isEqualTo(1105);
        assertThat(plugin.getExperienceForLevel(5)).isEqualTo(1416);
        assertThat(plugin.getExperienceForLevel(99)).isEqualTo(5201039);
    }
}