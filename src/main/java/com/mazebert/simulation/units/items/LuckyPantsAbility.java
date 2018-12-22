package com.mazebert.simulation.units.items;

import com.mazebert.simulation.units.abilities.LuckWithLevelBonusAbility;

public strictfp class LuckyPantsAbility extends LuckWithLevelBonusAbility {

    public LuckyPantsAbility() {
        super(0.2f, 0.002f);
    }

    @Override
    public String getTitle() {
        return "Get lucky";
    }
}
