package com.mazebert.simulation.plugins;

public strictfp class PlayerLevelPlugin {
    public static final int MAX_LEVEL = 200;
    private long[] playerLevelLookup = new long[MAX_LEVEL];

    public PlayerLevelPlugin() {
        for (int level = 0; level < MAX_LEVEL; ++level) {
            playerLevelLookup[level] = getExperienceForLevel(level + 1);
        }
    }

    public int getLevelForExperience(long experience) {
        for (int level = 1; level < MAX_LEVEL; ++level) {
            if (experience < playerLevelLookup[level]) {
                return level;
            }
        }
        return MAX_LEVEL;
    }

    public long getExperienceForLevel(int level) {
        if (level == 1) {
            return 0;
        }
        return Math.round(500.0 * StrictMath.pow(1.1, level - 2) + 250.0 * (level - 2));
    }
}
