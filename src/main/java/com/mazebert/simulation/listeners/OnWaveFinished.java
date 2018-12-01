package com.mazebert.simulation.listeners;

import com.mazebert.simulation.Wave;
import org.jusecase.signals.Signal;

public strictfp class OnWaveFinished extends Signal<OnWaveFinishedListener> {
    public OnWaveFinished() {
        super(OnWaveFinishedListener.class);
    }


    public void dispatch(Wave wave) {
        for (int i = 0; i < size; ++i) {
            listeners[i].onWaveFinished(wave);
        }
    }
}
