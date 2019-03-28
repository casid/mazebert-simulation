package com.mazebert.simulation.units.abilities;

import com.mazebert.simulation.units.towers.Tower;

public abstract strictfp class ActiveAbility extends Ability<Tower> {
    public boolean isReady() {
        return getReadyProgress() >= 1.0f;
    }

    public abstract float getReadyProgress();

    public abstract void activate();

    protected void onAbilityReady() {
        getUnit().onAbilityReady.dispatch(this);
    }
}
