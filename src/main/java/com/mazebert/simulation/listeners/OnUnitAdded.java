package com.mazebert.simulation.listeners;

import com.mazebert.simulation.units.Unit;

public strictfp class OnUnitAdded extends ExposedSignal<OnUnitAddedListener> {

    public void dispatch(Unit unit) {
        for (OnUnitAddedListener listener : this) {
            listener.onUnitAdded(unit);
        }

        if (isExposed()) {
            dispatchExposed(l -> l.onUnitAdded(unit));
        }
    }
}
