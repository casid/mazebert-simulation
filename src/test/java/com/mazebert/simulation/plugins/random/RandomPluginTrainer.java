package com.mazebert.simulation.plugins.random;

import static org.assertj.core.api.Assertions.assertThat;

public class RandomPluginTrainer implements RandomPlugin {

    private long seed;

    public void thenSeedIsSetTo(long expected) {
        assertThat(seed).isEqualTo(expected);
    }

    @Override
    public void setSeed(long seed) {
        this.seed = seed;
    }

    @Override
    public int nextInt() {
        return 0;
    }
}