package com.mazebert.simulation.units.items;

import com.mazebert.simulation.units.abilities.CritChanceWithLevelBonusAbility;

public strictfp class WetTowelAbility extends CritChanceWithLevelBonusAbility {
    public WetTowelAbility() {
        super(0.05f, 0.0f);
    }

    @Override
    public String getTitle() {
        return "Whooosh!";
    }
}
