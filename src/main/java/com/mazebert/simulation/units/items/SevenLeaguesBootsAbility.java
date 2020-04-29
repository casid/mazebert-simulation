package com.mazebert.simulation.units.items;

import com.mazebert.simulation.units.abilities.SpeedWithLevelBonusAbility;

public strictfp class SevenLeaguesBootsAbility extends SpeedWithLevelBonusAbility {
    public SevenLeaguesBootsAbility() {
        super(0.5f, 0.002f);
    }

    @Override
    public String getTitle() {
        return "Great Strides";
    }
}
