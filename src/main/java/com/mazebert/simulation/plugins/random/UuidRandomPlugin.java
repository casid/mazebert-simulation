package com.mazebert.simulation.plugins.random;

import java.util.UUID;

public strictfp class UuidRandomPlugin extends RandomPlugin {

    private FastRandomPlugin[] fastRandom = new FastRandomPlugin[4];
    private int nextIndex;

    public static UUID createSeed() {
        UUID uuid;
        int[] seeds;

        do {
            uuid = UUID.randomUUID();
            seeds = extractSeeds(uuid);
        } while (!validSeeds(seeds));

        return uuid;
    }

    public void setSeed(UUID seed) {
        int[] seeds = extractSeeds(seed);
        if (validSeeds(seeds)) {
            for (int i = 0; i < fastRandom.length; ++i) {
                fastRandom[i] = new FastRandomPlugin();
                fastRandom[i].setSeed(seeds[i]);
            }
        } else {
            throw new IllegalStateException("Random seeds must not contain 0x00000000!");
        }
    }

    private static boolean validSeeds(int[] seeds) {
        for (int seed : seeds) {
            if (seed == 0) {
                return false;
            }
        }
        return true;
    }

    private static int[] extractSeeds(UUID uuid) {
        int[] seeds = new int[4];

        long a = uuid.getMostSignificantBits();
        long b = uuid.getLeastSignificantBits();

        seeds[0] = (int)(a >>> 32);
        seeds[1] = (int)(a);
        seeds[2] = (int)(b >>> 32);
        seeds[3] = (int)(b);

        return seeds;
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
