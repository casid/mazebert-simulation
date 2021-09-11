package com.mazebert.simulation.units.abilities;

import com.mazebert.simulation.listeners.OnDamageListener;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class CullingStrikeAbility extends Ability<Tower> implements OnDamageListener {
    public static final float HEALTH_THRESHOLD = 0.1f;

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
        double health = target.getHealth();
        if (health > 0 && health <= getHealthThreshold() * target.getMaxHealth()) {
            getUnit().kill(target, true);
        }
    }

    protected float getHealthThreshold() {
        return HEALTH_THRESHOLD;
    }
}
