package com.mazebert.simulation.listeners;

import com.mazebert.simulation.units.creeps.Creep;
import org.jusecase.signals.Signal;

public strictfp class OnMiss extends Signal<OnMissListener> {
    public OnMiss() {
        super(OnMissListener.class);
    }

    public void dispatch(Object origin, Creep target) {
        for (int i = 0; i < size; ++i) {
            listeners[i].onMiss(origin, target);
        }
    }
}
