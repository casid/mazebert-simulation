package com.mazebert.simulation.listeners;

import com.mazebert.simulation.units.creeps.Creep;
import org.jusecase.signals.Signal;

public strictfp class OnDead extends Signal<OnDeadListener> {
    public void dispatch(Creep target) {
        for (OnDeadListener listener : getListeners()) {
            listener.onDead(target);
        }
    }
}
