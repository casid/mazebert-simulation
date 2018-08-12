package com.mazebert.simulation.plugins.random;

/**
 * Based on http://www.iquilezles.org/www/articles/sfrand/sfrand.htm
 * with some small improvements
 */
public strictfp class FastRandomPlugin implements RandomPlugin {
    private int seed;

    @Override
    public void setSeed(int seed) {
        this.seed = seed;
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
}
