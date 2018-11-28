package com.mazebert.simulation.units.items;

import com.mazebert.simulation.units.abilities.DamageWithLevelBonusAbility;

public strictfp class WellDoneSteakAbility extends DamageWithLevelBonusAbility {
    public WellDoneSteakAbility() {
        super(0.2f, 0.0f);
    }

    @Override
    public String getTitle() {
        return "Meet my meat!";
    }
}
