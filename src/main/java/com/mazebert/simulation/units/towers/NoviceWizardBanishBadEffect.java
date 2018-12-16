package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.creeps.effects.DurationEffect;

public strictfp class NoviceWizardBanishBadEffect extends DurationEffect {
    @Override
    protected void initialize(Creep unit) {
        super.initialize(unit);
        unit.addDamageModifier(-NoviceWizardSpell.banishmentAmplifier);
    }

    @Override
    protected void dispose(Creep unit) {
        unit.addDamageModifier(NoviceWizardSpell.banishmentAmplifier);
        super.dispose(unit);
    }
}
