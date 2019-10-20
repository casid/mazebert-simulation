package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.CardCategory;
import com.mazebert.simulation.units.abilities.AuraAbility;
import com.mazebert.simulation.units.creeps.Creep;

public strictfp class PhoenixBurn extends AuraAbility<Phoenix, Creep> {

    public PhoenixBurn() {
        super(CardCategory.Tower, Creep.class);
    }

    @Override
    protected void onAuraEntered(Creep unit) {
        unit.addAbility(new PhoenixBurnEffect(getUnit()));
    }

    @Override
    protected void onAuraLeft(Creep unit) {
        PhoenixBurnEffect ability = unit.getAbility(PhoenixBurnEffect.class, getUnit());
        if (ability != null) {
            unit.removeAbility(ability);
        }
    }
}
