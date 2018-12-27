package com.mazebert.simulation.listeners;

public strictfp class OnGameLoaded extends ExposedSignal<OnGameLoadedListener> {
    public void dispatch() {
        dispatchAll(OnGameLoadedListener::onGameLoaded);
    }
}
