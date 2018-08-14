package com.mazebert.simulation;

import com.mazebert.simulation.units.towers.Tower;

public strictfp class Balancing {
    public static final float GAME_COUNTDOWN_SECONDS = 2.0f;
    public static final float DAMAGE_BALANCING_FACTOR = 12.0f;

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
        int gold = (int)Math.round(Math.pow(1.0125, round) * 50.0);
        return gold > 1000 ? 1000 : gold;
    }
}
