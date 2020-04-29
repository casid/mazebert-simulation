package com.mazebert.simulation.units.items;

import com.mazebert.simulation.units.abilities.SpeedWithLevelBonusAbility;

public strictfp class PumpkinAbility extends SpeedWithLevelBonusAbility {
    public PumpkinAbility() {
        super(-1.0f, 0.0f);
    }

    @Override
    public String getTitle() {
        return "Distracted";
    }

    @Override
    public String getDescription() {
        return "While playing with the pumpkin:";
    }
}
