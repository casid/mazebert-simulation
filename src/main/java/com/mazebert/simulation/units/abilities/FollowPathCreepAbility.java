package com.mazebert.simulation.units.abilities;

import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.creeps.CreepState;

public strictfp class FollowPathCreepAbility extends FollowPathAbility<Creep> {
    @Override
    protected boolean isPossibleToWalk() {
        return getUnit().getState() == CreepState.Running || getUnit().getState() == CreepState.Knockback;
    }

    @Override
    protected float getSpeed() {
        return getUnit().getSpeed();
    }

    @Override
    protected void onTargetReached() {
        getUnit().onTargetReached.dispatch(getUnit());
    }
}
