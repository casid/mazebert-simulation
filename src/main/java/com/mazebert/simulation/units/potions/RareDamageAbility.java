package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.units.abilities.PermanentDamageWithLevelBonusAbility;

public strictfp class RareDamageAbility extends PermanentDamageWithLevelBonusAbility {
    public RareDamageAbility() {
        super(0.2f, 0.0016f);
    }

    @Override
    public String getTitle() {
        return "A Lot More Damage!";
    }
}
