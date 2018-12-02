package com.mazebert.simulation.listeners;

import org.jusecase.signals.Signal;

public strictfp class OnPause extends Signal<OnPauseListener> {
    public OnPause() {
        super(OnPauseListener.class);
    }

    public void dispatch(int playerId, boolean pause) {
        for (int i = 0; i < size; ++i) {
            listeners[i].onPause(playerId, pause);
        }
    }
}
