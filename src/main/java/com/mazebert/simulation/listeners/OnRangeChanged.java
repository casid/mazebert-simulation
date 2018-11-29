package com.mazebert.simulation.listeners;

import com.mazebert.simulation.units.towers.Tower;
import org.jusecase.signals.Signal;

public strictfp class OnRangeChanged extends Signal<OnRangeChangedListener> {
    public OnRangeChanged() {
        super(OnRangeChangedListener.class);
    }

    public void dispatch(Tower tower) {
        for (int i = 0; i < size; ++i) {
            listeners[i].onRangeChanged(tower);
        }
    }
}
