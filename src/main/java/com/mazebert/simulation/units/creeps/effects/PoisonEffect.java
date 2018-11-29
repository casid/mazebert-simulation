package com.mazebert.simulation.units.creeps.effects;

import com.mazebert.simulation.listeners.OnUpdateListener;
import com.mazebert.simulation.units.abilities.StackableAbility;
import com.mazebert.simulation.units.creeps.Creep;

public strictfp class PoisonEffect extends StackableAbility<Creep> implements OnUpdateListener {

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
    protected void updateStacks() {
        // not used
    }

    @Override
    public void onUpdate(float dt) {
        double damage = remainingDamage * dt / totalSeconds;
        getUnit().receiveDamage(damage);
        remainingDamage -= damage;

        remainingSeconds -= dt;
        if (remainingSeconds <= 0) {
            getUnit().removeAbility(this);
        }
    }

    public void addPoison(float duration, double damage) {
        totalSeconds = duration;
        remainingSeconds = duration;
        remainingDamage += damage;
    }
}
