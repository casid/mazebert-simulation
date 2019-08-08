package com.mazebert.simulation.listeners;

import com.mazebert.simulation.units.creeps.Creep;

public strictfp class OnMiss extends ExposedSignal<OnMissListener> {
    public void dispatch(Object origin, Creep target) {
        for (OnMissListener listener : this) {
            listener.onMiss(origin, target);
        }

        if (isExposed()) {
            dispatchExposed(l -> l.onMiss(origin, target));
        }
    }
}
