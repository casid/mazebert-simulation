package com.mazebert.simulation.listeners;

import org.jusecase.signals.Signal;

public strictfp class OnGameStarted extends Signal<OnGameStartedListener> {
    public OnGameStarted() {
        super(OnGameStartedListener.class);
    }

    public void dispatch() {
        for (int i = 0; i < size; ++i) {
            listeners[i].onGameStarted();
        }
    }
}
