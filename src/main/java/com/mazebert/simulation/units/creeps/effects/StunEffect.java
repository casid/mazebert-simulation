package com.mazebert.simulation.units.creeps.effects;

import com.mazebert.simulation.listeners.OnUpdateListener;
import com.mazebert.simulation.units.abilities.StackableAbility;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.creeps.CreepState;

public strictfp class StunEffect extends StackableAbility<Creep> implements OnUpdateListener {
    private float remainingSeconds;

    @Override
    protected void initialize(Creep unit) {
        super.initialize(unit);
        unit.onUpdate.add(this);

        unit.setState(CreepState.Hit);
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
            if (!getUnit().isDead()) {
                getUnit().setState(CreepState.Running);
            }
            getUnit().removeAbility(this);
        }
    }

    public void setDuration(float duration) {
        remainingSeconds = duration;
    }
}
