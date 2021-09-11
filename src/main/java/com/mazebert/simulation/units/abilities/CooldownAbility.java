package com.mazebert.simulation.units.abilities;

import com.mazebert.simulation.listeners.OnUpdateListener;
import com.mazebert.simulation.units.Unit;

public abstract strictfp class CooldownAbility<U extends Unit> extends Ability<U> implements OnUpdateListener {
    private float passedTime;

    @Override
    protected void initialize(U unit) {
        super.initialize(unit);
        unit.onUpdate.add(this);
    }

    @Override
    protected void dispose(U unit) {
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

    protected abstract float getCooldown();

    protected abstract boolean onCooldownReached();

    public float getProgress() {
        return StrictMath.min(passedTime / getCooldown(), 1.0f);
    }
}
