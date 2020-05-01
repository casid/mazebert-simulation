package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.units.abilities.PermanentSpeedWithLevelBonusAbility;

public strictfp class CommonSpeedAbility extends PermanentSpeedWithLevelBonusAbility {
    public CommonSpeedAbility() {
        super(0.04f, 0.0002f);
    }

    @Override
    public String getTitle() {
        return "A Bit More Speed";
    }
}
