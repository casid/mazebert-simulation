package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.CardCategory;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.units.abilities.AuraAbility;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.creeps.effects.StunEffect;

public strictfp class KiwiHakaAura extends AuraAbility<Kiwi, Creep> {
    private final KiwiHaka haka;

    public KiwiHakaAura(KiwiHaka haka) {
        super(Sim.context().version > Sim.v10 ? null : CardCategory.Tower, Creep.class);
        this.haka = haka;
    }

    @Override
    protected void onAuraEntered(Creep unit) {
        if (unit.isSteady() && Sim.context().isDoLSeasonContent()) {
            return;
        }

        StunEffect stunEffect = unit.addAbilityStack(StunEffect.class);
        stunEffect.setDuration(haka.getRemainingDuration());
    }

    @Override
    protected void onAuraLeft(Creep unit) {
        // unused
    }
}
