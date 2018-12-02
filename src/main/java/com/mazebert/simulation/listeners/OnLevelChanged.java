package com.mazebert.simulation.listeners;

import com.mazebert.simulation.units.Unit;

public strictfp class OnLevelChanged extends ExposedSignal<OnLevelChangedListener> {

    public void dispatch(Unit unit, int oldLevel, int newLevel) {
        for (OnLevelChangedListener listener : this) {
            listener.onLevelChanged(unit, oldLevel, newLevel);
        }

        if (isExposed()) {
            dispatchExposed(l -> l.onLevelChanged(unit, oldLevel, newLevel));
        }
    }
}
