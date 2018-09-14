package com.mazebert.simulation.listeners;

import com.mazebert.simulation.units.creeps.Creep;
import org.jusecase.signals.Signal;

public strictfp class OnTargetReached extends Signal<OnTargetReachedListener> {
    public OnTargetReached() {
        super(OnTargetReachedListener.class);
    }

    public void dispatch(Creep creep) {
        for (int i = 0; i < size; ++i) {
            listeners[i].onTargetReached(creep);
        }
    }
}
