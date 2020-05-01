package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.units.abilities.PermanentDropsWithLevelBonusAbility;

public strictfp class RareDropsAbility extends PermanentDropsWithLevelBonusAbility {
    public RareDropsAbility() {
        super(0.4f, 0.0016f, 0.4f, 0.0016f);
    }

    @Override
    public String getTitle() {
        return "Grand Tales of Treasure!";
    }
}
