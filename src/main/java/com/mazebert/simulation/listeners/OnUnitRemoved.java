package com.mazebert.simulation.listeners;

import com.mazebert.simulation.units.Unit;
import org.jusecase.signals.Signal;

public strictfp class OnUnitRemoved extends Signal<OnUnitRemovedListener> {
    public OnUnitRemoved() {
        super(OnUnitRemovedListener.class);
    }

    public void dispatch(Unit unit) {
        for (int i = 0; i < size; ++i) {
            listeners[i].onUnitRemoved(unit);
        }
    }
}
