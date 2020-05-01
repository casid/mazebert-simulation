package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.units.abilities.PermanentCritWithLevelBonusAbility;

public strictfp class RareCritAbility extends PermanentCritWithLevelBonusAbility {
    public RareCritAbility() {
        super(0.024f, 0.0008f, 0.4f, 0.004f);
    }

    @Override
    public String getTitle() {
        return "A Lot More Critical!";
    }
}
