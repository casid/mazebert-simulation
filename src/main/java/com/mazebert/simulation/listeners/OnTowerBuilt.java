package com.mazebert.simulation.listeners;

import com.mazebert.simulation.units.towers.Tower;
import org.jusecase.signals.Signal;

public strictfp class OnTowerBuilt extends Signal<OnTowerBuiltListener> {
    public void dispatch(Tower tower) {
        for (OnTowerBuiltListener listener : getListeners()) {
            listener.onTowerBuilt(tower);
        }
    }
}
