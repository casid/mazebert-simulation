package com.mazebert.simulation.units.items;

import com.mazebert.simulation.units.abilities.DamageWithLevelBonusAbility;

public strictfp class BeheaderDamageAbility extends DamageWithLevelBonusAbility {
    public BeheaderDamageAbility() {
        super(0.3f, 0.01f);
    }

    @Override
    public String getTitle() {
        return "Blood splatter";
    }
}
