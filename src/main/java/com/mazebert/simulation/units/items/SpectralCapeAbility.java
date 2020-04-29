package com.mazebert.simulation.units.items;

import com.mazebert.simulation.units.abilities.ArmorPenetrationWithLevelBonusAbility;

public strictfp class SpectralCapeAbility extends ArmorPenetrationWithLevelBonusAbility {
    public SpectralCapeAbility() {
        super(0.1f, 0.002f);
    }

    @Override
    public String getTitle() {
        return "";
    }
}
