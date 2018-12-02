package com.mazebert.simulation.listeners;

public strictfp class OnWaveCountDown extends ExposedSignal<OnWaveCountDownListener> {
    public void dispatch(int remainingSeconds) {
        dispatchAll(l -> l.onWaveCountDown(remainingSeconds));
    }
}
