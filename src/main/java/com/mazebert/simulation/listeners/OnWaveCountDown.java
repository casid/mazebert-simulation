package com.mazebert.simulation.listeners;

import org.jusecase.signals.Signal;

public strictfp class OnWaveCountDown extends Signal<OnWaveCountDownListener> {
    public OnWaveCountDown() {
        super(OnWaveCountDownListener.class);
    }

    public void dispatch(int remainingSeconds) {
        for (int i = 0; i < size; ++i) {
            listeners[i].onWaveCountDown(remainingSeconds);
        }
    }
}
