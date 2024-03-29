package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.units.abilities.PermanentCritWithLevelBonusAbility;

public strictfp class CommonCritAbility extends PermanentCritWithLevelBonusAbility {
    public CommonCritAbility() {
        super(0.008f, 0.0002f, 0.1f, 0.001f);
    }

    @Override
    public String getTitle() {
        return "A Bit More Critical";
    }
}
