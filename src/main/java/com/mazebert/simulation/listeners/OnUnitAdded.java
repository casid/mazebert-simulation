package com.mazebert.simulation.listeners;

import com.mazebert.simulation.units.Unit;
import org.jusecase.signals.Signal;

public strictfp class OnUnitAdded extends Signal<OnUnitAddedListener> {
    public OnUnitAdded() {
        super(OnUnitAddedListener.class);
    }

    public void dispatch(Unit unit) {
        for (int i = 0; i < size; ++i) {
            listeners[i].onUnitAdded(unit);
        }
    }
}
