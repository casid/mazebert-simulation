package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.units.abilities.PermanentCritWithLevelBonusAbility;

public class CommonCritAbility extends PermanentCritWithLevelBonusAbility {
    public CommonCritAbility() {
        super(0.008f, 0.0002f, 0.1f, 0.001f);
    }
}
