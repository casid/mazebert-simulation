package com.mazebert.simulation.units.items;

import com.mazebert.simulation.units.abilities.DamageWithLevelBonusAbility;

public strictfp class NorlsFurySwordAbility extends DamageWithLevelBonusAbility {
    public NorlsFurySwordAbility() {
        super(0.16f, 0.008f);
    }

    @Override
    public String getTitle() {
        return "It is still sharp...";
    }
}
