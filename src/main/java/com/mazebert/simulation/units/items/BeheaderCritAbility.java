package com.mazebert.simulation.units.items;

import com.mazebert.simulation.units.abilities.CritChanceWithLevelBonusAbility;

public strictfp class BeheaderCritAbility extends CritChanceWithLevelBonusAbility {
    public BeheaderCritAbility() {
        super(0.1f, 0.0f);
    }

    @Override
    public String getTitle() {
        return "Separation is Critical!";
    }
}
