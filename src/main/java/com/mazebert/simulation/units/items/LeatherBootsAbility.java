package com.mazebert.simulation.units.items;

import com.mazebert.simulation.units.abilities.SpeedWithLevelBonusAbility;

public strictfp class LeatherBootsAbility extends SpeedWithLevelBonusAbility {
    public LeatherBootsAbility() {
        super(0.08f, 0.0f);
    }

    @Override
    public String getTitle() {
        return "Smelly Boots, Speedy Feet";
    }
}
