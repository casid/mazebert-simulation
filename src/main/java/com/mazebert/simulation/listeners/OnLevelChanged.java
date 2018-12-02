package com.mazebert.simulation.listeners;

import com.mazebert.simulation.units.Unit;
import org.jusecase.signals.Signal;

public strictfp class OnLevelChanged extends Signal<OnLevelChangedListener> {

    public void dispatch(Unit unit, int oldLevel, int newLevel) {
        for (OnLevelChangedListener listener : this) {
            listener.onLevelChanged(unit, oldLevel, newLevel);
        }
    }
}
