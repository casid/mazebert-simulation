package com.mazebert.simulation.units.items;

import com.mazebert.simulation.units.abilities.DamageWithLevelBonusAbility;

public strictfp class WoodAxeAbility extends DamageWithLevelBonusAbility {
    public WoodAxeAbility() {
        super(0.2f, 0.0f);
    }

    @Override
    public String getTitle() {
        return "Chop, chop, chop";
    }
}
