package com.mazebert.simulation.units.traps;

import com.mazebert.simulation.units.Unit;

public abstract class Trap extends Unit {
    private int stackCount;

    public int getStackCount() {
        return stackCount;
    }

    public void setStackCount(int stackCount) {
        this.stackCount = stackCount;
    }

    public void addStack() {
        this.stackCount++;
    }
}
