package com.mazebert.simulation.listeners;

import org.jusecase.signals.Signal;

public strictfp class OnGameInitialized extends Signal<OnGameInitializedListener> {
    public OnGameInitialized() {
        super(OnGameInitializedListener.class);
    }

    public void dispatch() {
        for (int i = 0; i < size; ++i) {
            listeners[i].onGameInitialized();
        }
    }
}
