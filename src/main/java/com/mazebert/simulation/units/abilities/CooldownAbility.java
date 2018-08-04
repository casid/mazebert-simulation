package com.mazebert.simulation.units.abilities;

import com.mazebert.simulation.units.CooldownUnit;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.listeners.OnUpdateListener;

public abstract strictfp class CooldownAbility<U extends Unit & CooldownUnit> extends Ability<U> implements OnUpdateListener {
    private float passedTime;

    @Override
    protected void initialize(U unit) {
        super.initialize(unit);
        unit.onUpdate.add(this);
    }

    @Override
    public void dispose(U unit) {
        unit.onUpdate.remove(this);
        super.dispose(unit);
    }

    @Override
    public void onUpdate(float dt) {
        do {
            float cooldown = getCooldown();
            float increment = StrictMath.min(cooldown, dt);

            passedTime += increment;
            dt -= increment;

            if (passedTime >= cooldown) {
                if (onCooldownReached()) {
                    passedTime = 0.0f;
                }
            }
        } while (dt > 0);
    }

    protected float getCooldown() {
        return getUnit().getCooldown();
    }

    abstract boolean onCooldownReached();
}
