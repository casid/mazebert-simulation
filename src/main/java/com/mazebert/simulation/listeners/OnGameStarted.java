package com.mazebert.simulation.listeners;

import com.mazebert.simulation.units.creeps.Creep;
import org.jusecase.signals.Signal;

public strictfp class OnGameStarted extends Signal<OnGameStartedListener> {
    public void dispatch() {
        for (OnGameStartedListener listener : getListeners()) {
            listener.onGameStarted();
        }
    }
}
