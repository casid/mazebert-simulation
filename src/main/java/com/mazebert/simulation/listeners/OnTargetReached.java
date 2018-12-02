package com.mazebert.simulation.listeners;

import com.mazebert.simulation.units.creeps.Creep;
import org.jusecase.signals.Signal;

public strictfp class OnTargetReached extends Signal<OnTargetReachedListener> {
    public void dispatch(Creep creep) {
        for (OnTargetReachedListener listener : this) {
            listener.onTargetReached(creep);
        }
    }
}
