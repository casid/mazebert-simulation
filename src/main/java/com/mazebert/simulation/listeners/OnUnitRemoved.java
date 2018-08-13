package com.mazebert.simulation.listeners;

import com.mazebert.simulation.units.Unit;
import org.jusecase.signals.Signal;

public strictfp class OnUnitRemoved extends Signal<OnUnitRemovedListener> {
    public void dispatch(Unit unit) {
        for (OnUnitRemovedListener listener : getListeners()) {
            listener.onUnitRemoved(unit);
        }
    }
}
