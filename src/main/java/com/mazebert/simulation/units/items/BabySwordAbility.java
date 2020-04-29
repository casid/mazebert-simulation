package com.mazebert.simulation.units.items;

import com.mazebert.simulation.units.abilities.DamageWithLevelBonusAbility;

public strictfp class BabySwordAbility extends DamageWithLevelBonusAbility {
    public BabySwordAbility() {
        super(0.1f, 0.0f);
    }

    @Override
    public String getTitle() {
        return "En Garde";
    }
}
