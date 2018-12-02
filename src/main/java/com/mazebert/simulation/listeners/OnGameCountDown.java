package com.mazebert.simulation.listeners;

public strictfp class OnGameCountDown extends ExposedSignal<OnGameCountDownListener> {
    public void dispatch(int remainingSeconds) {
        dispatchAll(l -> l.onGameCountDown(remainingSeconds));
    }
}
