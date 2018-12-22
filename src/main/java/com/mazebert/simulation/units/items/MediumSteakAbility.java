package com.mazebert.simulation.units.items;

import com.mazebert.simulation.units.abilities.DamageWithLevelBonusAbility;

public strictfp class MediumSteakAbility extends DamageWithLevelBonusAbility {
    public MediumSteakAbility() {
        super(0.25f, 0.005f);
    }

    @Override
    public String getTitle() {
        return "Die by the meat";
    }
}
