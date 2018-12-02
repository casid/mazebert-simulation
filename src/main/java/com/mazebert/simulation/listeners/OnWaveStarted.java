package com.mazebert.simulation.listeners;

public strictfp class OnWaveStarted extends ExposedSignal<OnWaveStartedListener> {
    public void dispatch() {
        dispatchAll(OnWaveStartedListener::onWaveStarted);
    }
}
