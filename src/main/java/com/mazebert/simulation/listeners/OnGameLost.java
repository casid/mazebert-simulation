package com.mazebert.simulation.listeners;

public strictfp class OnGameLost extends ExposedSignal<OnGameLostListener> {
    public void dispatch() {
        dispatchAll(OnGameLostListener::onGameLost);
    }
}
