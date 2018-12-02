package com.mazebert.simulation.listeners;

import com.mazebert.simulation.Wave;

public strictfp class OnWaveFinished extends ExposedSignal<OnWaveFinishedListener> {
    public void dispatch(Wave wave) {
        dispatchAll(l -> l.onWaveFinished(wave));
    }
}
