package com.mazebert.simulation.listeners;

import org.jusecase.signals.Signal;

public strictfp class OnPause extends Signal<OnPauseListener> {

    public void dispatch(int playerId, boolean pause) {
        for (OnPauseListener listener : this) {
            listener.onPause(playerId, pause);
        }
    }
}
