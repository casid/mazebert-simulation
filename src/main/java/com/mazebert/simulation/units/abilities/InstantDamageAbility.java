package com.mazebert.simulation.units.abilities;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.listeners.OnAttackListener;
import com.mazebert.simulation.systems.DamageSystem;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class InstantDamageAbility extends Ability<Tower> implements DamageAbility, OnAttackListener {
    protected final DamageSystem damageSystem = Sim.context().damageSystem;

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        unit.onAttack.add(this);
    }

    @Override
    protected void dispose(Tower unit) {
        unit.onAttack.remove(this);
        super.dispose(unit);
    }

    @Override
    public void onAttack(Creep target) {
        damageSystem.dealDamage(this, getUnit(), target);
    }

    @Override
    public boolean isOriginalDamage() {
        return true;
    }
}
