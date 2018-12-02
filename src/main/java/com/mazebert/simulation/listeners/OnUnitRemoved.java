package com.mazebert.simulation.listeners;

import com.mazebert.simulation.units.Unit;

public strictfp class OnUnitRemoved extends ExposedSignal<OnUnitRemovedListener> {
    public void dispatch(Unit unit) {
        for (OnUnitRemovedListener listener : this) {
            listener.onUnitRemoved(unit);
        }

        if (isExposed()) {
            dispatchExposed(l -> l.onUnitRemoved(unit));
        }
    }
}
