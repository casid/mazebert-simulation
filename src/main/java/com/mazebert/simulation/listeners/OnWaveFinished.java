package com.mazebert.simulation.listeners;

import com.mazebert.simulation.Wave;
import org.jusecase.signals.Signal;

public strictfp class OnWaveFinished extends Signal<OnWaveFinishedListener> {
    public void dispatch(Wave wave) {
        for (OnWaveFinishedListener listener : this) {
            listener.onWaveFinished(wave);
        }
    }
}
