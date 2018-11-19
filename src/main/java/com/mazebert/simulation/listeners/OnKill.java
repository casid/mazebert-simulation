package com.mazebert.simulation.listeners;

import com.mazebert.simulation.units.creeps.Creep;
import org.jusecase.signals.Signal;

public strictfp class OnKill extends Signal<OnKillListener> {
    public OnKill() {
        super(OnKillListener.class);
    }

    public void dispatch(Creep target) {
        for (int i = 0; i < size; ++i) {
            listeners[i].onKill(target);
        }
    }
}
