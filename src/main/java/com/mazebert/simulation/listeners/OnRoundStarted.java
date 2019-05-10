package com.mazebert.simulation.listeners;

import org.jusecase.signals.Signal;

public strictfp class OnRoundStarted extends Signal<OnRoundStartedListener> {
    public void dispatch(int round) {
        for (OnRoundStartedListener listener : this) {
            listener.onRoundStarted(round);
        }
    }
}
