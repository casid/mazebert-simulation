package com.mazebert.simulation.listeners;

import com.mazebert.simulation.units.creeps.Creep;
import org.jusecase.signals.Signal;

public strictfp class OnMiss extends Signal<OnMissListener> {
    public void dispatch(Object origin, Creep target) {
        for (OnMissListener listener : this) {
            listener.onMiss(origin, target);
        }
    }
}
