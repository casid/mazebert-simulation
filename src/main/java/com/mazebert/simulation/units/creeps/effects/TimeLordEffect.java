package com.mazebert.simulation.units.creeps.effects;

import com.mazebert.simulation.listeners.OnDamageListener;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.creeps.Creep;

public strictfp class TimeLordEffect extends Ability<Creep> implements OnDamageListener {
    @Override
    protected void initialize(Creep unit) {
        super.initialize(unit);
        unit.onDamage.add(this);

        unit.setSteady(true);
        unit.setSpeedModifier(0.25f);
    }

    @Override
    protected void dispose(Creep unit) {
        unit.onDamage.remove(this);
        super.dispose(unit);
    }

    @Override
    public void onDamage(Object origin, Creep target, double damage, int multicrits) {
        // TODO convert damage to bonus round seconds
    }
}
