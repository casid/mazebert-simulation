package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.units.abilities.PermanentSpeedWithLevelBonusAbility;

public strictfp class RareSpeedAbility extends PermanentSpeedWithLevelBonusAbility {
    public RareSpeedAbility() {
        super(0.16f, 0.0008f);
    }
}
