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
        return "Perks of Membership";
    }

    @Override
    public String getDescription() {
        return "Members gain " + format.percent(StonecuttersAuraEffect.getDamageBonus()) + "% damage against <c=#9da9bc>Fal</c> per total member level.";
    }

    @Override
    public String getLevelBonus() {
        return "At level 99, damage bonus counts against <c=#5d8b4c>Ber</c>, <c=#9da9bc>Fal</c> and <c=#760983>Vex</c> as well.";
    }

    @Override
    public String getIconFile() {
        return "stone1_512";
    }
}
