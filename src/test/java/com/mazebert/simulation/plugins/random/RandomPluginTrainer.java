package com.mazebert.simulation.plugins.random;

import static org.assertj.core.api.Assertions.assertThat;

public class RandomPluginTrainer implements RandomPlugin {

    private int seed;
    private float[] floatAbs;
    private int nextFloatAbs;

    public void givenFloatAbs(float... floats) {
        for (float f : floats) {
            assertThat(f).isLessThan(1.0f);
            assertThat(f).isGreaterThanOrEqualTo(0.0f);
        }
        floatAbs = floats;
        nextFloatAbs = 0;
    }

    public void thenSeedIsSetTo(int expected) {
        assertThat(seed).isEqualTo(expected);
    }

    @Override
    public void setSeed(int seed) {
        this.seed = seed;
    }

    @Override
    public float getFloat() {
        return 0;
    }

    @Override
    public float getFloatAbs() {
        if (floatAbs == null) {
            return 0.0f;
        }
        if (nextFloatAbs >= floatAbs.length - 1) {
            return floatAbs[floatAbs.length - 1];
        }
        return floatAbs[nextFloatAbs++];
    }
}