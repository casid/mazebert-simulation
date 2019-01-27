package com.mazebert.simulation.plugins.random;

import java.util.UUID;

public strictfp class UuidRandomPlugin extends RandomPlugin {

    private FastRandomPlugin[] fastRandom = new FastRandomPlugin[4];
    private int nextIndex;

    public void setSeed(UUID seed) {
        int[] seeds = GameIdGenerator.extractSeeds(seed);
        if (GameIdGenerator.validSeeds(seeds)) {
            for (int i = 0; i < fastRandom.length; ++i) {
                fastRandom[i] = new FastRandomPlugin();
                fastRandom[i].setSeed(seeds[i]);
            }
        } else {
            throw new IllegalStateException("Random seeds must not contain 0x00000000!");
        }
    }

    int getInternalSeed(int index) {
        return fastRandom[index].getSeed();
    }

    @Override
    public float getFloat() {
        return nextRandom().getFloat();
    }

    @Override
    public float getFloatAbs() {
        return nextRandom().getFloatAbs();
    }

    @Override
    public UUID getUuid() {
        return nextRandom().getUuid();
    }

    private FastRandomPlugin nextRandom() {
        FastRandomPlugin next = fastRandom[nextIndex++];
        if (nextIndex >= 4) {
            nextIndex = 0;
        }
        return next;
    }
}
