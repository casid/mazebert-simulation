package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.units.abilities.CritChanceWithLevelBonusAbility;

public strictfp class WetTowelAbility extends CritChanceWithLevelBonusAbility {
    public WetTowelAbility() {
        super(Sim.context().version >= Sim.vDoLEnd ? 0.08f : 0.05f, 0.0f);
    }

    @Override
    public String getTitle() {
        return "Slap!";
    }
}
