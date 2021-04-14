package com.mazebert.simulation.listeners;

public strictfp class OnWaveStarted extends ExposedSignal<OnWaveStartedListener> {
    public void dispatch(int skippedSeconds) {
        dispatchAll(l -> l.onWaveStarted(skippedSeconds));
    }
}
