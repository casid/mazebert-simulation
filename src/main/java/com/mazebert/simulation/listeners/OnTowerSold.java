package com.mazebert.simulation.listeners;

import org.jusecase.signals.Signal;

public strictfp class OnTowerSold extends Signal<OnTowerSoldListener> {
    public void dispatch() {
        for (OnTowerSoldListener listener : this) {
            listener.onTowerSold();
        }
    }
}
