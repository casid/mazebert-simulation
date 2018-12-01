package com.mazebert.simulation.listeners;

import com.mazebert.simulation.units.Unit;
import org.jusecase.signals.Signal;

public strictfp class OnLevelChanged extends Signal<OnLevelChangedListener> {
    public OnLevelChanged() {
        super(OnLevelChangedListener.class);
    }

    public void dispatch(Unit unit, int oldLevel, int newLevel) {
        for (int i = 0; i < size; ++i) {
            listeners[i].onLevelChanged(unit, oldLevel, newLevel);
        }
    }
}
