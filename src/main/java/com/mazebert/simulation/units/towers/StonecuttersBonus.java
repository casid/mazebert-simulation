package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.units.abilities.Ability;

/**
 * For display only
 */
public strictfp class StonecuttersBonus extends Ability<Tower> {
    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Member bonus";
    }

    @Override
    public String getDescription() {
        return "Every member gains " + format.percent(StonecuttersAuraEffect.DAMAGE_BONUS) + "% damage against <c=#9da9bc>Fal</c> per total member level.";
    }

    @Override
    public String getLevelBonus() {
        return "Damage bonus counts for <c=#5d8b4c>Ber</c>, <c=#9da9bc>Fal</c> and <c=#760983>Vex</c>. (requires level 99)";
    }

    @Override
    public String getIconFile() {
        return "stone1_512";
    }
}
