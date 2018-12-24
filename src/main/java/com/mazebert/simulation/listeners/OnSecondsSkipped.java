package com.mazebert.simulation.listeners;

import org.jusecase.signals.Signal;

public strictfp class OnSecondsSkipped extends Signal<OnSecondsSkippedListener> {
    public void dispatch() {
        for (OnSecondsSkippedListener listener : this) {
            listener.onSecondsSkipped();
        }
    }
}
