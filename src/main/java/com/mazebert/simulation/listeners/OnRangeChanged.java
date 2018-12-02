package com.mazebert.simulation.listeners;

import com.mazebert.simulation.units.towers.Tower;
import org.jusecase.signals.Signal;

public strictfp class OnRangeChanged extends Signal<OnRangeChangedListener> {
    public void dispatch(Tower tower) {
        for (OnRangeChangedListener listener : this) {
            listener.onRangeChanged(tower);
        }
    }
}
