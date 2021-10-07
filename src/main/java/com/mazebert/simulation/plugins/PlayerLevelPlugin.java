package com.mazebert.simulation.plugins;

import com.mazebert.simulation.Sim;

public strictfp class PlayerLevelPlugin {
    private final int maxLevel = Sim.context().version >= Sim.vRnR ? 300: 200;
    private final long[] playerLevelLookup = new long[maxLevel];

    public PlayerLevelPlugin() {
        for (int level = 0; level < maxLevel; ++level) {
            playerLevelLookup[level] = getExperienceForLevel(level + 1);
        }
    }

    public int getLevelForExperience(long experience) {
        for (int level = 1; level < maxLevel; ++level) {
            if (experience < playerLevelLookup[level]) {
                return level;
            }
        }
        return maxLevel;
    }

    public long getExperienceForLevel(int level) {
        if (level == 1) {
            return 0;
        }
        return StrictMath.round(500.0 * StrictMath.pow(1.1, level - 2) + 250.0 * (level - 2));
    }
}
