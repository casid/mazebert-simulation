package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.units.abilities.AuraAbility;

public strictfp class GaneshaAura extends AuraAbility<Tower, Tower> {
    public GaneshaAura() {
        super(Tower.class, 3);
    }

    @Override
    protected void onAuraEntered(Tower unit) {
        unit.addAbility(new GaneshaAuraEffect(getUnit()));
    }

    @Override
    protected void onAuraLeft(Tower unit) {
        GaneshaAuraEffect ability = unit.getAbility(GaneshaAuraEffect.class, getUnit());
        if (ability != null) {
            unit.removeAbility(ability);
        }
    }
}
