package com.mazebert.simulation.plugins;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FormatPluginTest {
    FormatPlugin format = new FormatPlugin();

    @Test
    void cooldown() {
        assertThat(format.cooldown(0.0f)).isEqualTo("0s");
        assertThat(format.cooldown(1.0f)).isEqualTo("1s");
        assertThat(format.cooldown(2.0f)).isEqualTo("2s");
        assertThat(format.cooldown(2.2f)).isEqualTo("2.2s");
        assertThat(format.cooldown(2.234f)).isEqualTo("2.2s");
        assertThat(format.cooldown(0.55f)).isEqualTo("0.6s");
    }

    @Test
    void damage() {
        assertThat(format.damage(0.0)).isEqualTo("0");
        assertThat(format.damage(1.0)).isEqualTo("1");
        assertThat(format.damage(599.4)).isEqualTo("599");
        assertThat(format.damage(599.6)).isEqualTo("599");
        assertThat(format.damage(1000.0)).isEqualTo("1,000");
        assertThat(format.damage(1000.4)).isEqualTo("1,000");
        assertThat(format.damage(999999.0)).isEqualTo("999,999");
        assertThat(format.damage(1000000.0)).isEqualTo("1M");
        assertThat(format.damage(1232000.0)).isEqualTo("1.2M");
        assertThat(format.damage(24790000.0)).isEqualTo("24.8M");
        assertThat(format.damage(13400000000.0)).isEqualTo("13.4B");
        assertThat(format.damage(788890000000000.0)).isEqualTo("788.9T");
        assertThat(format.damage(7888900400000000000.0)).isEqualTo("7,888,900T");
    }

    @Test
    void money() {
        assertThat(format.money(Long.MAX_VALUE)).isEqualTo("9,223,372T");
    }

    @Test
    void percent() {
        assertThat(format.percent(0.0f)).isEqualTo("0");
        assertThat(format.percent(1.0f)).isEqualTo("100");
        assertThat(format.percent(1.001f)).isEqualTo("100");
        assertThat(format.percent(0.001f)).isEqualTo("0.1");
        assertThat(format.percent(0.0001f)).isEqualTo("0.01");
        assertThat(format.percent(0.00001f)).isEqualTo("0.001");
        assertThat(format.percent(0.000001f)).isEqualTo("0.0001");
        assertThat(format.percent(0.0000001f)).isEqualTo("0.00001");
        assertThat(format.percent(0.00000001f)).isEqualTo("0");
    }
}