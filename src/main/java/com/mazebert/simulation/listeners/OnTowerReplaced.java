package com.mazebert.simulation.listeners;

import com.mazebert.simulation.units.towers.Tower;
import org.jusecase.signals.Signal;

public strictfp class OnTowerReplaced extends Signal<OnTowerReplacedListener> {
    public void dispatch(Tower oldTower) {
        for (OnTowerReplacedListener listener : this) {
            listener.onTowerReplaced(oldTower);
        }
    }
}
