package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.units.abilities.AttackAbility;

/**
 * Cannot be handled properly via the attack ability, cause we want this guy to fire multiple times at the same direction,
 * regardless whether the creep is still alive or not.
 * Thus, this class is merely an UI ability.
 * The actual implementation is in the burst ability.
 */
public strictfp class ScarFaceAttack extends AttackAbility {
    public static final float CHANCE = 0.3f;
    public static final float CHANCE_PER_LEVEL = 0.003f;
    public static final int MAX_SHOTS = 256;

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Burst";
    }

    @Override
    public String getDescription() {
        return "After firing, there is a " + format.percent(CHANCE) + "% chance to fire another shot.";
    }

    @Override
    public String getLevelBonus() {
        return "+" + format.percent(CHANCE_PER_LEVEL) + "% chance per level.";
    }

    @Override
    public String getIconFile() {
        return "0048_taunt_512";
    }
}
