package com.mazebert.simulation.units.traps;

import com.mazebert.simulation.CardCategory;
import com.mazebert.simulation.units.abilities.AuraAbility;
import com.mazebert.simulation.units.creeps.Creep;

public strictfp class BearHunterTrapAura extends AuraAbility<BearHunterTrap, Creep> {
    public BearHunterTrapAura() {
        super(CardCategory.Tower, Creep.class, 0.5f);
    }

    @Override
    protected void onAuraEntered(Creep unit) {
        getUnit().trigger(unit);
    }

    @Override
    protected void onAuraLeft(Creep unit) {
    }
}
