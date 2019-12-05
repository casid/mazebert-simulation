package com.mazebert.simulation.units.items;

import com.mazebert.simulation.units.abilities.BossDamageWithLevelBonusAbility;

public strictfp class HeroicCapeAbility extends BossDamageWithLevelBonusAbility {
    public HeroicCapeAbility() {
        super(0.1f, 0.001f);
    }

    @Override
    public String getTitle() {
        return "Big Ego";
    }
}
