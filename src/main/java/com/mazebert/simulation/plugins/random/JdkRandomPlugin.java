package com.mazebert.simulation.plugins.random;

import java.util.Random;

public strictfp class JdkRandomPlugin implements RandomPlugin {
    private final Random random = new Random();

    @Override
    public void setSeed(long seed) {
        random.setSeed(seed);
    }

    @Override
    public int nextInt() {
        return random.nextInt();
    }
}
