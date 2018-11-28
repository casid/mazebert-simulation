package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.units.abilities.PermanentSpeedWithLevelBonusAbility;

public strictfp class UncommonSpeedAbility extends PermanentSpeedWithLevelBonusAbility {
    public UncommonSpeedAbility() {
        super(0.08f, 0.0004f);
    }
}
