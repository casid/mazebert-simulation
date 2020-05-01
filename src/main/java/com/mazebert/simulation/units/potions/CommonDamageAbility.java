package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.units.abilities.PermanentDamageWithLevelBonusAbility;

public strictfp class CommonDamageAbility extends PermanentDamageWithLevelBonusAbility {
    public CommonDamageAbility() {
        super(0.05f, 0.0004f);
    }

    @Override
    public String getTitle() {
        return "A Bit More Damage";
    }
}
