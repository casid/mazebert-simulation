package com.mazebert.simulation;

import com.mazebert.simulation.units.towers.Tower;

public strictfp class Balancing {
    public static final float GAME_COUNTDOWN_SECONDS = 30.0f;
    public static final float WAVE_COUNTDOWN_SECONDS = 5.0f;
    public static final float BONUS_COUNTDOWN_SECONDS = 60.0f;
    public static final float TIME_LORD_COUNTDOWN_SECONDS = 60.0f;
    public static final int TIME_LORD_ENCOUNTER_SECONDS = 5000;
    public static final int BONUS_SPAWN_COUNTDOWN_SECONDS = 5;
    public static final float EARLY_CALL_COUNTDOWN_SECONDS = 5.0f;
    public static final float DAMAGE_BALANCING_FACTOR = 12.0f;
    public static final float MAX_COOLDOWN = 60.0f;
    public static final float MIN_COOLDOWN = 0.01f;
    public static final float MAX_TRIGGER_CHANCE = 0.8f;
    public static final float STARTING_CRIT_CHANCE = 0.05f;
    public static final float STARTING_CRIT_DAMAGE = 0.25f;
    public static final int MAX_TOWER_LEVEL = 99;
    public static final float DEFAULT_DROP_CHANCE = 0.03f;
    public static final float DROP_CHANCE_CONST = 0.5f;
    public static final float DROP_QUALITY_CONST = 0.4f;
    public static final int STARTING_GOLD = 150;
    public static final float GOLD_INTEREST = 0.02f;
    public static final float GOLD_RETURN_WHEN_TOWER_SOLD = 0.8f;
    public static final int MAX_GOLD_INTEREST = 1000000;
    public static final float PENALTY_FOR_LEAKING_ENTIRE_ROUND = 0.5f;
    public static final int MAX_ELEMENTS = 2;

    private static final float[] towerExperienceForLevel = new float[MAX_TOWER_LEVEL];

    static {
        for (int i = 0; i < MAX_TOWER_LEVEL; ++i) {
            towerExperienceForLevel[i] = getTowerExperienceForLevel(i + 1);
        }
    }

    public static float getBaseDamage(Tower tower) {
        return StrictMath.round(1.0f + tower.getStrength() * getDamageFactorForRange(tower.getBaseRange()) * (getLinearCreepHitpoints(tower.getLevel()) * tower.getBaseCooldown()) / DAMAGE_BALANCING_FACTOR);
    }

    /**
     * Damage factor for the given range (either increase or decrease the tower damage).
     */
    public static float getDamageFactorForRange(float r) {
        return 3.0f / ((r * 2.0f) + 1.0f);
    }

    public static float getLinearCreepHitpoints(int round) {
        int x = round - 1;
        return 64 * x + 256;
    }

    public static int getGoldForRound(int round, int version) {
        double rawGold = StrictMath.pow(1.0125, round) * 50.0;
        int gold = (int) StrictMath.round(rawGold);

        if (version >= Sim.v16) {
            if (rawGold > 1000.0) {
                return 1000;
            }
        } else {
            if (gold > 1000) {
                return 1000;
            }
        }

        return gold;
    }

    public static double getTotalCreepHitpoints(int version, int round, Difficulty difficulty, int playerCount) {
        double x = round - 1;

        double midGameFactor = difficulty.midGameFactor;
        double earlyGameFactor = difficulty.earlyGameFactor;
        if (playerCount > 1 && version >= Sim.vDoL) {
            int factor = playerCount - 1;
            earlyGameFactor += 0.25 * factor;
            midGameFactor += 0.0003 * factor;
        }

        // Add endgame hitpoints if we are there yet!
        double endgameHitpoints = 0.0;
        if (x >= 140.0) {
            double endgameX = x - 140;
            endgameHitpoints = difficulty.endGameFactor * endgameX * endgameX * endgameX * endgameX;
        }

        double hp = endgameHitpoints + midGameFactor * x * x * x * x + earlyGameFactor * x * x + getLinearCreepHitpoints(round);
        if (playerCount > 1 && version >= Sim.v20) {
            hp *= playerCount;
        }
        if (version >= Sim.v15) {
            return hp;
        } else {
            return StrictMath.round(hp);
        }
    }

    public static float getExperienceForRound(int version, int round, WaveType waveType, int playerCount) {
        float experience = getExperienceForRound(round);

        if (playerCount > 1 && version >= Sim.v20) {
            experience *= playerCount;
        }

        switch (waveType) {
            case Horseman:
                return 1.4f * experience;
            case Challenge:
                return 0;
            case MassChallenge:
                return 1.2f * experience;
            default:
                return experience;
        }
    }

    private static float getExperienceForRound(int round) {
        float experience = 20.0f * (float) StrictMath.pow(1.015, round);

        // Cap experience at the amount you gain for wave 200.
        if (experience > 400) {
            experience = 400;
        }

        return experience;
    }

    public static int getTowerLevelForExperience(float experience) {
        for (int i = 0; i < towerExperienceForLevel.length; ++i) {
            if (experience < towerExperienceForLevel[i]) {
                return i;
            }
        }
        return MAX_TOWER_LEVEL;
    }

    public static float getTowerExperienceForLevel(int level) {
        if (level <= 1) {
            return 0;
        }

        float result = 20.0f;
        float totalResult = 0.0f;
        for (int i = 1; i < level; ++i) {
            totalResult += result;
            result *= 1.05550;
        }

        return totalResult;
    }
}
