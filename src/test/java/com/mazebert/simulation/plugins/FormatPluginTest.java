package com.mazebert.simulation.plugins;

import com.mazebert.simulation.Balancing;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.units.Currency;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FormatPluginTest {
    FormatPlugin format = new FormatPlugin();

    @Test
    void cooldown() {
        assertThat(format.cooldown(0.2f)).isEqualTo("0.2s");
        assertThat(format.cooldown(1.0f)).isEqualTo("1s");
        assertThat(format.cooldown(2.0f)).isEqualTo("2s");
        assertThat(format.cooldown(2.2f)).isEqualTo("2.2s");
        assertThat(format.cooldown(2.234f)).isEqualTo("2.2s");
        assertThat(format.cooldown(0.55f)).isEqualTo("0.6s");
        assertThat(format.cooldown(Balancing.MAX_COOLDOWN)).isEqualTo("60s (max)");
        assertThat(format.cooldown(Balancing.MIN_COOLDOWN)).isEqualTo("0.01s (min)");
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
    void damage_crit() {
        assertThat(format.damage(599.4, 0)).isEqualTo("599");
        assertThat(format.damage(599.4, 1)).isEqualTo("599!");
        assertThat(format.damage(599.4, 2)).isEqualTo("599!!");
        assertThat(format.damage(599.4, 3)).isEqualTo("599!!!");
        assertThat(format.damage(599.4, 4)).isEqualTo("599!!!!");
    }

    @Test
    void gold() {
        assertThat(format.gold(Long.MAX_VALUE)).isEqualTo("9,223,372T");
    }

    @Test
    void gold_between() {
        assertThat(format.gold(10, Currency.Cookie, " more ")).isEqualTo("10 more cookies");
    }

    @Test
    void goldGain() {
        assertThat(format.goldGain(1000)).isEqualTo("+1,000");
        assertThat(format.goldGain(-1000)).isEqualTo("-1,000");
    }

    @Test
    void percent() {
        assertThat(format.percent(0.0f)).isEqualTo("0");
        assertThat(format.percent(1.0f)).isEqualTo("100");
        assertThat(format.percent(1.001f)).isEqualTo("100.1");
        assertThat(format.percent(0.001f)).isEqualTo("0.1");
        assertThat(format.percent(0.0001f)).isEqualTo("0.01");
        assertThat(format.percent(0.00001f)).isEqualTo("0.001");
        assertThat(format.percent(0.000001f)).isEqualTo("0.0001");
        assertThat(format.percent(0.0000001f)).isEqualTo("0.00001");
        assertThat(format.percent(0.00000001f)).isEqualTo("0");
        assertThat(format.percent(0.3999999f)).isEqualTo("40");
        assertThat(format.percent(-0.00000001f)).isEqualTo("0");
        assertThat(format.percent(0.0016f)).isEqualTo("0.16");
    }

    @Test
    void name() {
        assertThat(format.percent(0.0000001f)).isEqualTo("0.00001");
    }

    @Test
    void experienceWithSignAndUnit() {
        assertThat(format.experienceWithSignAndUnit(1)).isEqualTo("+1XP");
        assertThat(format.experienceWithSignAndUnit(1.2f)).isEqualTo("+1.2XP");
        assertThat(format.experienceWithSignAndUnit(10.2f)).isEqualTo("+10.2XP");
        assertThat(format.experienceWithSignAndUnit(120f)).isEqualTo("+120XP");
        assertThat(format.experienceWithSignAndUnit(-20f)).isEqualTo("-20XP");
        assertThat(format.experienceWithSignAndUnit(0.01f)).isEqualTo("+0.01XP");
    }

    @Test
    void kills() {
        assertThat(format.kills(Long.MAX_VALUE)).isEqualTo("9,223,372T");
    }

    @Test
    void time() {
        assertThat(format.time(0)).isEqualTo("0s");
        assertThat(format.time(0.1f)).isEqualTo("0s");
        assertThat(format.time(2)).isEqualTo("2s");
        assertThat(format.time(60)).isEqualTo("1m");
        assertThat(format.time(63)).isEqualTo("1m");
        assertThat(format.time(120)).isEqualTo("2m");
        assertThat(format.time(3600)).isEqualTo("1:00h");
        assertThat(format.time(3720)).isEqualTo("1:02h");
        assertThat(format.time(3600 * 2 + 60 * 23)).isEqualTo("2:23h");
    }

    @Test
    void colored() {
        assertThat(format.colored("text", 0x123456)).isEqualTo("<c=#123456>text</c>");
        assertThat(format.colored("text", 0xfac301)).isEqualTo("<c=#fac301>text</c>");
        assertThat(format.colored("text", 0x038174)).isEqualTo("<c=#038174>text</c>");
    }

    @Test
    void rarity() {
        assertThat(format.rarity(Rarity.Common)).isEqualTo("<c=#fefefe>common</c>");
        assertThat(format.rarity(Rarity.Common, false)).isEqualTo("<c=#fefefe>Common</c>");
    }

    @Test
    void distance() {
        assertThat(format.distance(0)).isEqualTo("0 tiles");
        assertThat(format.distance(0.5f)).isEqualTo("0.5 tiles");
        assertThat(format.distance(1)).isEqualTo("1 tile");
        assertThat(format.distance(2)).isEqualTo("2 tiles");
    }
}