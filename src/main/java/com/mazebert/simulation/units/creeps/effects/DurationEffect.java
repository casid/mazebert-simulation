package com.mazebert.simulation.units.creeps.effects;

import com.mazebert.simulation.listeners.OnUpdateListener;
import com.mazebert.simulation.units.abilities.StackableAbility;
import com.mazebert.simulation.units.creeps.Creep;

public strictfp abstract class DurationEffect extends StackableAbility<Creep> implements OnUpdateListener {
    private float remainingSeconds;

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
        remainingSeconds -= dt;
        if (remainingSeconds <= 0) {
            getUnit().removeAbility(this);
        }
    }

    public void setDuration(float duration) {
        remainingSeconds = duration;
    }

    @Override
    protected void updateStacks() {
        // not used
    }
}
