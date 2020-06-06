package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.units.abilities.AttackAbility;

/**
 * Cannot be handled properly via the attack ability, cause we want this guy to fire multiple times at the same direction,
 * regardless whether the creep is still alive or not.
 * Thus, this class is merely an UI ability.
 * The actual implementation is in the burst ability.
 */
public strictfp class ScarFaceAttack extends AttackAbility {

    public static final int MAX_SHOTS = 256;

    private final ScarFaceBurst burst;

    public ScarFaceAttack(ScarFaceBurst burst) {
        this.burst = burst;
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Rat-a-tat-tat!";
    }

    @Override
    public String getDescription() {
        return "After each attack, there is a " + format.percent(burst.getChance()) + "% chance to fire another shot.";
    }

    @Override
    public String getLevelBonus() {
        return "+" + format.percent(burst.getChancePerLevel()) + "% chance per level.";
    }

    @Override
    public String getIconFile() {
        return "0048_taunt_512";
    }
}
