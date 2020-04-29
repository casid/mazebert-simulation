package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.ArmorType;
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
        return "Members gain " + format.percent(StonecuttersAuraEffect.getDamageBonus()) + "% damage against " + format.armorType(ArmorType.Fal) + " per total member level.";
    }

    @Override
    public String getLevelBonus() {
        return "At level 99, damage bonus counts against " + format.armorType(ArmorType.Ber) + ", " + format.armorType(ArmorType.Fal) + " and " + format.armorType(ArmorType.Vex) + " as well.";
    }

    @Override
    public String getIconFile() {
        return "stone1_512";
    }
}
