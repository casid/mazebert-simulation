package com.mazebert.simulation.units.abilities;

import com.mazebert.simulation.listeners.OnDamageListener;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.creeps.effects.PoisonEffect;
import com.mazebert.simulation.units.towers.Tower;

public abstract strictfp class PoisonAbility extends Ability<Tower> implements OnDamageListener {
    private final Class<? extends PoisonEffect> effectClass;
    private final float duration;

    public PoisonAbility(Class<? extends PoisonEffect> effectClass, float duration) {
        this.effectClass = effectClass;
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
        PoisonEffect poisonEffect = target.addAbilityStack(effectClass);
        poisonEffect.addPoison(duration, calculatePoisonDamage(target, damage, multicrits));
    }

    protected abstract double calculatePoisonDamage(Creep target, double damage, int multicrits);
}
