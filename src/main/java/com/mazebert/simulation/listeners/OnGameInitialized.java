package com.mazebert.simulation.listeners;

import org.jusecase.signals.Signal;

public strictfp class OnGameInitialized extends Signal<OnGameInitializedListener> {
    public void dispatch() {
        for (OnGameInitializedListener listener : this) {
            listener.onGameInitialized();
        }
    }
}
