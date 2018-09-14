package com.mazebert.simulation.listeners;

import org.jusecase.signals.Signal;

public strictfp class OnWaveStarted extends Signal<OnWaveStartedListener> {
    public OnWaveStarted() {
        super(OnWaveStartedListener.class);
    }


    public void dispatch() {
        for (int i = 0; i < size; ++i) {
            listeners[i].onWaveStarted();
        }
    }
}
