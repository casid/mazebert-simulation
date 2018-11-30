package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.units.abilities.SpeedWithLevelBonusAbility;

public strictfp class BearHunterSpeed extends SpeedWithLevelBonusAbility {
    public BearHunterSpeed() {
        super(0, 0.005f);
    }

    @Override
    public boolean isVisibleToUser() {
        return false;
    }
}
