package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.units.abilities.AirDamageWithLevelBonusAbility;

public strictfp class CandleAirDamage extends AirDamageWithLevelBonusAbility {
    public CandleAirDamage() {
        super(0.5f, 0.01f);
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getIconFile() {
        return "0067_fireball_512";
    }
}
