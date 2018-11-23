package com.mazebert.simulation.units.items;

import com.mazebert.simulation.units.abilities.DamageWithLevelBonusAbility;

public strictfp class WoodenStaffAbility extends DamageWithLevelBonusAbility {
    public WoodenStaffAbility() {
        super(0.05f, 0.0f);
    }

    @Override
    public String getTitle() {
        return "Knock, knock!";
    }
}
