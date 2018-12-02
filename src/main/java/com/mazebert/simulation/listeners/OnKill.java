package com.mazebert.simulation.listeners;

import com.mazebert.simulation.units.creeps.Creep;
import org.jusecase.signals.Signal;

public strictfp class OnKill extends Signal<OnKillListener> {
    public void dispatch(Creep target) {
        for (OnKillListener listener : this) {
            listener.onKill(target);
        }
    }
}
