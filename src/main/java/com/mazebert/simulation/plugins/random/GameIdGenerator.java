package com.mazebert.simulation.plugins.random;

import java.util.UUID;

@SuppressWarnings("unused")
public strictfp class GameIdGenerator {

    public static UUID create() {
        UUID uuid;
        int[] seeds;

        do {
            uuid = UUID.randomUUID();
            seeds = extractSeeds(uuid);
        } while (!validSeeds(seeds));

        return uuid;
    }

    public static boolean validSeeds(int[] seeds) {
        for (int seed : seeds) {
            if (seed == 0) {
                return false;
            }
        }
        return true;
    }

    public static int[] extractSeeds(UUID uuid) {
        int[] seeds = new int[4];

        long a = uuid.getMostSignificantBits();
        long b = uuid.getLeastSignificantBits();

        seeds[0] = (int)(a >>> 32);
        seeds[1] = (int)(a);
        seeds[2] = (int)(b >>> 32);
        seeds[3] = (int)(b);

        return seeds;
    }
}
