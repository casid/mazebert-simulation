package com.mazebert.simulation.listeners;

import org.jusecase.signals.Signal;

public strictfp class OnGameCountDown extends Signal<OnGameCountDownListener> {
    public void dispatch(int remainingSeconds) {
        for (OnGameCountDownListener listener : this) {
            listener.onGameCountDown(remainingSeconds);
        }
    }
}
