package com.mazebert.simulation.units.items;

import com.mazebert.simulation.listeners.OnDamageListener;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class FrozenSetSlowAbility extends Ability<Tower> implements OnDamageListener {
    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        unit.onDamage.add(this);
    }

    @Override
    protected void dispose(Tower unit) {
        unit.onDamage.remove(this);
        super.dispose(unit);
    }

    @Override
    public void onDamage(Object origin, Creep target, double damage, int multicrits) {
        FrozenSetSlowEffect effect = target.addAbilityStack(getUnit(), FrozenSetSlowEffect.class);
        effect.addStack(0.9f, 5.0f, 1);
    }
}
