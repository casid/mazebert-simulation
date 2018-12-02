package com.mazebert.simulation.listeners;

import com.mazebert.simulation.units.creeps.Creep;

public strictfp class OnKill extends ExposedSignal<OnKillListener> {
    public void dispatch(Creep target) {
        for (OnKillListener listener : this) {
            listener.onKill(target);
        }

        if (isExposed()) {
            dispatchExposed(l -> l.onKill(target));
        }
    }
}
