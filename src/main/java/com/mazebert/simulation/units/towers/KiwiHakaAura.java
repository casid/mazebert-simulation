package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.units.abilities.AuraAbility;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.creeps.effects.StunEffect;

public strictfp class KiwiHakaAura extends AuraAbility<Kiwi, Creep> {
    private final KiwiHaka haka;

    public KiwiHakaAura(KiwiHaka haka) {
        super(Creep.class);
        this.haka = haka;
    }

    @Override
    protected void onAuraEntered(Creep unit) {
        StunEffect stunEffect = unit.addAbilityStack(StunEffect.class);
        stunEffect.setDuration(haka.getRemainingDuration());
    }

    @Override
    protected void onAuraLeft(Creep unit) {
        // unused
    }
}
