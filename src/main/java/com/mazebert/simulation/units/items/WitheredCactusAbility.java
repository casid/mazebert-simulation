package com.mazebert.simulation.units.items;

import com.mazebert.simulation.units.abilities.ArmorPenetrationWithLevelBonusAbility;

public strictfp class WitheredCactusAbility extends ArmorPenetrationWithLevelBonusAbility {
    public WitheredCactusAbility() {
        super(0.15f, 0.003f);
    }

    @Override
    public String getTitle() {
        return "One Thousand Stiches";
    }
}
