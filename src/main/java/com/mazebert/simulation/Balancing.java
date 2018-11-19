package com.mazebert.simulation;

import com.mazebert.simulation.units.towers.Tower;

public strictfp class Balancing {
    public static final float GAME_COUNTDOWN_SECONDS = 2.0f; // TODO change to 30
    public static final float WAVE_COUNTDOWN_SECONDS = 5.0f;
    public static final float DAMAGE_BALANCING_FACTOR = 12.0f;
    public static final float MAX_COOLDOWN = 60.0f;
    public static final float MIN_COOLDOWN = 0.01f;
    public static final float MAX_TRIGGER_CHANCE = 0.8f;

    public static float getBaseDamage(Tower tower) {
        return StrictMath.round(1.0f + tower.getStrength() * getDamageFactorForRange(tower.getBaseRange()) * (getLinearCreepHitpoints(tower.getLevel()) * tower.getBaseCooldown()) / DAMAGE_BALANCING_FACTOR);
    }

    /**
     * Damage factor for the given range (either increase or decrease the tower damage).
     */
    public static float getDamageFactorForRange(float r ) {
        return 3.0f / ((r * 2.0f) + 1.0f);
    }

    public static float getLinearCreepHitpoints(int round) {
        int x = round - 1;
        return 64 * x + 256;
    }

    public static int getGoldForRound(int round) {
        int gold = (int)StrictMath.round(StrictMath.pow(1.0125, round) * 50.0);
        return gold > 1000 ? 1000 : gold;
    }

    public static double getTotalCreepHitpoints(int round, Difficulty difficulty) {
        double x = round - 1;

        // Add endgame hitpoints if we are there yet!
        double endgameHitpoints = 0.0;
        if (x >= 140.0)
        {
            double endgameX = x - 140;
            endgameHitpoints = difficulty.endGameFactor * endgameX * endgameX * endgameX * endgameX;
        }

        return StrictMath.round(endgameHitpoints + difficulty.midGameFactor * x * x * x * x + difficulty.earlyGameFactor * x * x + getLinearCreepHitpoints(round));
    }

    public static float getExperienceForRound(int round, WaveType waveType) {
        float experience = getExperienceForRound(round);

        switch (waveType) {
            case Horseman:
                return 1.4f * experience;
            case Challenge:
                return 0;
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

    public static int getTowerLevelForExperience(double experience) {
        // TODO
        return 0;
    }
}
