package com.mazebert.simulation.units.abilities;

import com.mazebert.simulation.listeners.OnDamageListener;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.creeps.effects.PoisonEffect;
import com.mazebert.simulation.units.towers.Tower;

public abstract strictfp class PoisonAbility extends Ability<Tower> implements OnDamageListener {
    private final float duration;

    public PoisonAbility(float duration) {
        this.duration = duration;
    }

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        unit.onDamage.add(this);
    }

    @Override
    protected void dispose(Tower unit) {
        super.dispose(unit);
        unit.onDamage.remove(this);
    }

    @Override
    public void onDamage(Object origin, Creep target, double damage, int multicrits) {
        if (origin instanceof PoisonEffect) {
            return;
        }

        PoisonEffect poisonEffect = target.addAbilityStack(getUnit(), PoisonEffect.class);
        if (poisonEffect != null) {
            poisonEffect.addPoison(duration, calculatePoisonDamage(target, damage, multicrits));
        }
    }

    public float getDuration() {
        return duration;
    }

    protected abstract double calculatePoisonDamage(Creep target, double damage, int multicrits);
}
