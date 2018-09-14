package com.mazebert.simulation.listeners;

import com.mazebert.simulation.units.creeps.Creep;
import org.jusecase.signals.Signal;

public strictfp class OnDeath extends Signal<OnDeathListener> {
    public OnDeath() {
        super(OnDeathListener.class);
    }

    public void dispatch(Creep target) {
        for (int i = 0; i < size; ++i) {
            listeners[i].onDeath(target);
        }
    }
}
