package com.mazebert.simulation.units.creeps.effects;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.listeners.OnUpdateListener;
import com.mazebert.simulation.systems.DamageSystem;
import com.mazebert.simulation.units.abilities.StackableByOriginAbility;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class PoisonEffect extends StackableByOriginAbility<Creep> implements OnUpdateListener {

    private final DamageSystem damageSystem = Sim.context().damageSystem;

    private float totalSeconds;
    private float remainingSeconds;
    private double remainingDamage;

    @Override
    protected void initialize(Creep unit) {
        super.initialize(unit);
        unit.onUpdate.add(this);
    }

    @Override
    protected void dispose(Creep unit) {
        unit.onUpdate.remove(this);
        super.dispose(unit);
    }

    @Override
    public void onUpdate(float dt) {
        if (getUnit().isDead()) {
            getUnit().removeAbility(this);
        } else {
            double damage = remainingDamage * dt / totalSeconds;
            damageSystem.dealDamage(this, (Tower) getOrigin(), getUnit(), damage, 0);
            remainingDamage -= damage;

            remainingSeconds -= dt;
            if (remainingSeconds <= 0) {
                getUnit().removeAbility(this);
            }
        }
    }

    public void addPoison(float duration, double damage) {
        totalSeconds = duration;
        remainingSeconds = duration;
        remainingDamage += damage;
    }
}
