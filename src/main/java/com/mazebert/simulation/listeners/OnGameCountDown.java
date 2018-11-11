package com.mazebert.simulation.listeners;

import org.jusecase.signals.Signal;

public strictfp class OnGameCountDown extends Signal<OnGameCountDownListener> {
    public OnGameCountDown() {
        super(OnGameCountDownListener.class);
    }

    public void dispatch(int remainingSeconds) {
        for (int i = 0; i < size; ++i) {
            listeners[i].onGameCountDown(remainingSeconds);
        }
    }
}
