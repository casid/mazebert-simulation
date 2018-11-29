package com.mazebert.simulation.units.abilities;

import com.mazebert.simulation.units.Unit;

public strictfp class StackableByOriginAbility<U extends Unit> extends Ability<U> {
    private Object origin;

    public void setOrigin(Object origin) {
        this.origin = origin;
    }

    public Object getOrigin() {
        return origin;
    }
}
