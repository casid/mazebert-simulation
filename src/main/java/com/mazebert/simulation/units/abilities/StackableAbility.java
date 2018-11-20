package com.mazebert.simulation.units.abilities;

import com.mazebert.simulation.units.Unit;

public abstract strictfp class StackableAbility<U extends Unit> extends Ability<U> {
    private int stackCount = 1;

    public void addStack() {
        ++stackCount;
    }

    public int getStackCount() {
        return stackCount;
    }

    public void removeStack() {
        --stackCount;
    }
}
