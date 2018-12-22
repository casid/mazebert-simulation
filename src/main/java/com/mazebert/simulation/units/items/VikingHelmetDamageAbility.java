package com.mazebert.simulation.units.items;

import com.mazebert.simulation.units.abilities.DamageWithLevelBonusAbility;

public strictfp class VikingHelmetDamageAbility extends DamageWithLevelBonusAbility {

    public VikingHelmetDamageAbility() {
        super(0.5f, 0.01f);
    }
}
