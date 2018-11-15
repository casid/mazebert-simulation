package com.mazebert.simulation.plugins.random;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class RandomPluginTrainer extends UuidRandomPlugin {

    private UUID seed;
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

    public void thenSeedIsSetTo(UUID expected) {
        assertThat(seed).isEqualTo(expected);
    }

    @Override
    public void setSeed(UUID seed) {
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

    @Override
    public UUID getUuid() {
        return new UUID(0, 0);
    }
}