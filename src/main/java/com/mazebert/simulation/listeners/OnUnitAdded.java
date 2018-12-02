package com.mazebert.simulation.listeners;

import com.mazebert.simulation.units.Unit;
import org.jusecase.signals.Signal;

public strictfp class OnUnitAdded extends Signal<OnUnitAddedListener> {

    public void dispatch(Unit unit) {
        for (OnUnitAddedListener listener : this) {
            listener.onUnitAdded(unit);
        }
    }
}
