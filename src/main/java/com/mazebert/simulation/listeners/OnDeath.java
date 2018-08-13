package com.mazebert.simulation.listeners;

import com.mazebert.simulation.units.creeps.Creep;
import org.jusecase.signals.Signal;

public strictfp class OnDeath extends Signal<OnDeathListener> {
    public void dispatch(Creep target) {
        for (OnDeathListener listener : getListeners()) {
            listener.onDeath(target);
        }
    }
}
