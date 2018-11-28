package com.mazebert.simulation.units.abilities;

import com.mazebert.simulation.units.Unit;

public abstract strictfp class StackableAbility<U extends Unit> extends Ability<U> {
    private int stackCount = 1;

    public final void addStack() {
        setStackCount(stackCount + 1);
    }

    public final void removeStack() {
        setStackCount(stackCount - 1);
    }

    public final int getStackCount() {
        return stackCount;
    }

    public final void setStackCount(int stackCount) {
        this.stackCount = stackCount;
        updateStacks();
    }

    protected abstract void updateStacks();
}
