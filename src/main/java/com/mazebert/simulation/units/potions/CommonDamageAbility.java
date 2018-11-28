package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.units.abilities.PermanentDamageWithLevelBonusAbility;

public class CommonDamageAbility extends PermanentDamageWithLevelBonusAbility {
    public CommonDamageAbility() {
        super(0.05f, 0.0004f);
    }
}
