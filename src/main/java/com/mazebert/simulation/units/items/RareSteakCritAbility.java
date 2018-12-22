package com.mazebert.simulation.units.items;

import com.mazebert.simulation.units.abilities.CritChanceWithLevelBonusAbility;

public strictfp class RareSteakCritAbility extends CritChanceWithLevelBonusAbility {
    public RareSteakCritAbility() {
        super(0.1f, 0.0f);
    }

    @Override
    public String getTitle() {
        return "Raw meat is critical";
    }
}
