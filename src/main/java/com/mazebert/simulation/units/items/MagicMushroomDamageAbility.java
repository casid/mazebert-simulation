package com.mazebert.simulation.units.items;

import com.mazebert.simulation.units.abilities.DamageWithLevelBonusAbility;

public strictfp class MagicMushroomDamageAbility extends DamageWithLevelBonusAbility {

    public MagicMushroomDamageAbility() {
        super(-MagicMushroomAbility.damageReduction, 0.0f);
    }

    @Override
    public boolean isVisibleToUser() {
        return false;
    }
}
