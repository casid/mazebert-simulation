package com.mazebert.simulation.listeners;

public strictfp class OnGameStarted extends ExposedSignal<OnGameStartedListener> {
    public void dispatch() {
        dispatchAll(OnGameStartedListener::onGameStarted);
    }
}
