package com.mazebert.simulation.units.abilities;

import com.mazebert.simulation.listeners.OnDamageListener;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.creeps.effects.StunEffect;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class StunAbility extends Ability<Tower> implements OnDamageListener {
    private float chance;
    private float duration;

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
        if (target.isDead()) {
            return;
        }
        if (!(origin instanceof DamageAbility)) {
            return;
        }

        if (getUnit().isAbilityTriggered(chance)) {
            StunEffect stunEffect = target.addAbilityStack(StunEffect.class);
            stunEffect.setDuration(duration);
        }
    }

    public float getChance() {
        return chance;
    }

    public void setChance(float chance) {
        this.chance = chance;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Stun";
    }

    @Override
    public String getDescription() {
        return "Each attack has a " + formatPlugin.percent(chance) + "% chance to stun the creep for " + duration + "s.";
    }
}
