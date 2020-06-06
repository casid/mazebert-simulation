package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.units.abilities.CritChanceWithLevelBonusAbility;

public strictfp class MonsterTeethAbility extends CritChanceWithLevelBonusAbility {
    public MonsterTeethAbility() {
        super(Sim.context().version >= Sim.vDoLEnd ? 0.2f : 0.15f, 0);
    }

    @Override
    public String getTitle() {
        return "Critical Bite";
    }
}
