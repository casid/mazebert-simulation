package com.mazebert.simulation.units.items;

import com.mazebert.simulation.units.abilities.ItemChanceWithLevelBonusAbility;

public strictfp class RingOfGreedAbility extends ItemChanceWithLevelBonusAbility {
    public RingOfGreedAbility() {
        super(0.14f, 0.0f);
    }

    @Override
    public String getTitle() {
        return "Treasure hunter";
    }
}
