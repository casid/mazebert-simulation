package com.mazebert.simulation.listeners;

public strictfp class OnTimeLordStarted extends ExposedSignal<OnTimeLordStartedListener> {
    public void dispatch() {
        dispatchAll(OnTimeLordStartedListener::onTimeLordStarted);
    }
}
