package com.mazebert.simulation.plugins.random;

import java.util.UUID;

/**
 * Based on <a href="http://www.iquilezles.org/www/articles/sfrand/sfrand.htm">iquilezles article</a>
 * with some small improvements
 */
public strictfp class FastRandomPlugin extends RandomPlugin {
    private int seed;

    public void setSeed(int seed) {
        this.seed = seed;
    }

    public int getSeed() {
        return seed;
    }

    @Override
    public float getFloat() {
        seed *= 16807;
        return Float.intBitsToFloat((seed & 0x007fffff) | 0x40000000) - 3.0f;
    }

    @Override
    public float getFloatAbs() {
        seed *= 16807;
        return Float.intBitsToFloat((seed & 0x007fffff) | 0x3f800000) - 1.0f;
    }

    @Override
    public UUID getUuid() {
        return new UUID(getLongBits(), getLongBits());
    }

    private long getLongBits() {
        int a = seed *= 16807;
        int b = seed *= 16807;
        return (((long) a) << 32) | (b & 0xffffffffL);
    }
}
