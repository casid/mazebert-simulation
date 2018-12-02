package com.mazebert.simulation.listeners;

import org.jusecase.signals.Signal;

public strictfp class OnWaveCountDown extends Signal<OnWaveCountDownListener> {
    public void dispatch(int remainingSeconds) {
        for (OnWaveCountDownListener listener : this) {
            listener.onWaveCountDown(remainingSeconds);
        }
    }
}
