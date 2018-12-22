package com.mazebert.simulation.units.items;

import com.mazebert.simulation.units.abilities.GoldWithLevelBonusAbility;

public strictfp class GoldCoinsAbility extends GoldWithLevelBonusAbility {
    public GoldCoinsAbility() {
        super(0.2f, 0.005f);
    }

    @Override
    public String getTitle() {
        return "Attractive coins";
    }
}
