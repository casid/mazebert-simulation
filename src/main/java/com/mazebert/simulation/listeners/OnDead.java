package com.mazebert.simulation.listeners;

import com.mazebert.simulation.units.creeps.Creep;
import org.jusecase.signals.Signal;

public strictfp class OnDead extends Signal<OnDeadListener> {
    public OnDead() {
        super(OnDeadListener.class);
    }

    public void dispatch(Creep target) {
        for (int i = 0; i < size; ++i) {
            listeners[i].onDead(target);
        }
    }
}
