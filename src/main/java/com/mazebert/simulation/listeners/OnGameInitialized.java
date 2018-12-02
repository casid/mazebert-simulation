package com.mazebert.simulation.listeners;

public strictfp class OnGameInitialized extends ExposedSignal<OnGameInitializedListener> {
    public void dispatch() {
        dispatchAll(OnGameInitializedListener::onGameInitialized);
    }
}
