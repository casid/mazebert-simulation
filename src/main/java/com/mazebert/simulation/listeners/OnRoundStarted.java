package com.mazebert.simulation.listeners;

import com.mazebert.simulation.Wave;
import org.jusecase.signals.Signal;

public strictfp class OnRoundStarted extends Signal<OnRoundStartedListener> {
    public void dispatch(Wave wave) {
        for (OnRoundStartedListener listener : this) {
            listener.onRoundStarted(wave);
        }
    }
}
