package com.mazebert.simulation.listeners;

import com.mazebert.simulation.units.towers.Tower;

public strictfp class OnInventorySizeChanged extends ExposedSignal<OnInventorySizeChangedListener> {
    public void dispatch(Tower tower) {
        for (OnInventorySizeChangedListener listener : this) {
            listener.onInventorySizeChanged(tower);
        }

        if (isExposed()) {
            dispatchExposed(l -> l.onInventorySizeChanged(tower));
        }
    }
}
