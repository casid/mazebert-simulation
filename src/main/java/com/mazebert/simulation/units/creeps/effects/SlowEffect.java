package com.mazebert.simulation.units.creeps.effects;

import com.mazebert.simulation.listeners.OnUpdateListener;
import com.mazebert.simulation.units.abilities.StackableByOriginAbility;
import com.mazebert.simulation.units.creeps.Creep;

public strictfp class SlowEffect extends StackableByOriginAbility<Creep> implements OnUpdateListener {
    private int stackCount;
    private float totalMultiplier = 1;
    private float remainingSeconds;

    @Override
    protected void initialize(Creep unit) {
        super.initialize(unit);
        unit.onUpdate.add(this);
    }

    @Override
    protected void dispose(Creep unit) {
        getUnit().setSpeedModifier(getUnit().getSpeedModifier() / totalMultiplier);

        unit.onUpdate.remove(this);
        super.dispose(unit);
    }

    public boolean addStack(float slowMultiplier, float duration, int maxStacks) {
        remainingSeconds = duration;
        if (stackCount < maxStacks) {
            totalMultiplier *= slowMultiplier;
            getUnit().setSpeedModifier(getUnit().getSpeedModifier() * slowMultiplier);
            ++stackCount;

            return true;
        }
        return false;
    }

    @Override
    public void onUpdate(float dt) {
        if (getUnit().isDead()) {
            getUnit().removeAbility(this);
        } else {
            remainingSeconds -= dt;
            if (remainingSeconds <= 0.0f) {
                getUnit().removeAbility(this);
            }
        }
    }

    public int getStackCount() {
        return stackCount;
    }
}
