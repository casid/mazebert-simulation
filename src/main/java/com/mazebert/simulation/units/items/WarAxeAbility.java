package com.mazebert.simulation.units.items;

import com.mazebert.simulation.units.abilities.DamageWithLevelBonusAbility;

public strictfp class WarAxeAbility extends DamageWithLevelBonusAbility {
    public WarAxeAbility() {
        super(0.25f, 0.005f);
    }

    @Override
    public String getTitle() {
        return "Axetinction";
    }
}
