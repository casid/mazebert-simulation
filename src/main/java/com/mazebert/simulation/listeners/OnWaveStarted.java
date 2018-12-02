package com.mazebert.simulation.listeners;

import org.jusecase.signals.Signal;

public strictfp class OnWaveStarted extends Signal<OnWaveStartedListener> {
    public void dispatch() {
        for (OnWaveStartedListener listener : this) {
            listener.onWaveStarted();
        }
    }
}
