package com.mazebert.simulation.units.items;

import com.mazebert.simulation.units.abilities.AirDamageWithLevelBonusAbility;

public strictfp class LongBowAbility extends AirDamageWithLevelBonusAbility {
    public LongBowAbility() {
        super(0.24f, 0.006f);
    }

    @Override
    public String getTitle() {
        return "Hunt 'Em Down";
    }
}
