package com.mazebert.simulation.listeners;

import com.mazebert.simulation.units.towers.Tower;
import org.jusecase.signals.Signal;

public strictfp class OnTowerBuilt extends Signal<OnTowerBuiltListener> {
    public void dispatch(Tower oldTower) {
        for (OnTowerBuiltListener listener : this) {
            listener.onTowerBuilt(oldTower);
        }
    }
}
