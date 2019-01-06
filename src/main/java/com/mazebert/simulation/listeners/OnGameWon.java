package com.mazebert.simulation.listeners;

public strictfp class OnGameWon extends ExposedSignal<OnGameWonListener> {
    public void dispatch() {
        dispatchAll(OnGameWonListener::onGameWon);
    }
}
