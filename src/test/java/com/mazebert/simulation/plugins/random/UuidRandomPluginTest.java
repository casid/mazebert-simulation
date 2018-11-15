package com.mazebert.simulation.plugins.random;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

strictfp class UuidRandomPluginTest {
    UuidRandomPlugin randomPlugin = new UuidRandomPlugin();

    @Test
    void seedSplitting() {
        UUID uuid = new UUID(0x0123456789abcdefL, 0xfedcba9876543210L);

        randomPlugin.setSeed(uuid);

        assertThat(randomPlugin.getInternalSeed(0)).inHexadecimal().isEqualTo(0x01234567);
        assertThat(randomPlugin.getInternalSeed(1)).inHexadecimal().isEqualTo(0x89abcdef);
        assertThat(randomPlugin.getInternalSeed(2)).inHexadecimal().isEqualTo(0xfedcba98);
        assertThat(randomPlugin.getInternalSeed(3)).inHexadecimal().isEqualTo(0x76543210);
    }

    @Test
    void seedSplitting_zero() {
        UUID uuid = new UUID(0x0000000089abcdefL, 0xfedcba9876543210L);

        Throwable throwable = catchThrowable(() -> randomPlugin.setSeed(uuid));

        assertThat(throwable).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void randoms() {
        UUID uuid = new UUID(0x0123456789abcdefL, 0xfedcba9876543210L);
        randomPlugin.setSeed(uuid);

        assertThat(randomPlugin.getFloat()).isEqualTo(-0.4777105f);
        assertThat(randomPlugin.getInternalSeed(0)).inHexadecimal().isEqualTo(0xb2a16d31); // new seed
        assertThat(randomPlugin.getInternalSeed(1)).inHexadecimal().isEqualTo(0x89abcdef);
        assertThat(randomPlugin.getInternalSeed(2)).inHexadecimal().isEqualTo(0xfedcba98);
        assertThat(randomPlugin.getInternalSeed(3)).inHexadecimal().isEqualTo(0x76543210);

        assertThat(randomPlugin.getFloat()).isEqualTo(0.45348573f);
        assertThat(randomPlugin.getInternalSeed(0)).inHexadecimal().isEqualTo(0xb2a16d31);
        assertThat(randomPlugin.getInternalSeed(1)).inHexadecimal().isEqualTo(0x6e5d05e9); // new seed
        assertThat(randomPlugin.getInternalSeed(2)).inHexadecimal().isEqualTo(0xfedcba98);
        assertThat(randomPlugin.getInternalSeed(3)).inHexadecimal().isEqualTo(0x76543210);

        assertThat(randomPlugin.getFloat()).isEqualTo(0.47370338f);
        assertThat(randomPlugin.getInternalSeed(0)).inHexadecimal().isEqualTo(0xb2a16d31);
        assertThat(randomPlugin.getInternalSeed(1)).inHexadecimal().isEqualTo(0x6e5d05e9);
        assertThat(randomPlugin.getInternalSeed(2)).inHexadecimal().isEqualTo(0x4d5e5128); // new seed
        assertThat(randomPlugin.getInternalSeed(3)).inHexadecimal().isEqualTo(0x76543210);

        assertThat(randomPlugin.getFloat()).isEqualTo(-0.45749283f);
        assertThat(randomPlugin.getInternalSeed(0)).inHexadecimal().isEqualTo(0xb2a16d31);
        assertThat(randomPlugin.getInternalSeed(1)).inHexadecimal().isEqualTo(0x6e5d05e9);
        assertThat(randomPlugin.getInternalSeed(2)).inHexadecimal().isEqualTo(0x4d5e5128);
        assertThat(randomPlugin.getInternalSeed(3)).inHexadecimal().isEqualTo(0x91a2b870); // new seed

        assertThat(randomPlugin.getFloat()).isEqualTo(-0.8801291f);
        assertThat(randomPlugin.getInternalSeed(0)).inHexadecimal().isEqualTo(0x8407abf7); // new seed
        assertThat(randomPlugin.getInternalSeed(1)).inHexadecimal().isEqualTo(0x6e5d05e9);
        assertThat(randomPlugin.getInternalSeed(2)).inHexadecimal().isEqualTo(0x4d5e5128);
        assertThat(randomPlugin.getInternalSeed(3)).inHexadecimal().isEqualTo(0x91a2b870);
    }
}