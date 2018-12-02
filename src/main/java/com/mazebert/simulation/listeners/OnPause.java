package com.mazebert.simulation.listeners;

public strictfp class OnPause extends ExposedSignal<OnPauseListener> {

    public void dispatch(int playerId, boolean pause) {
        dispatchAll(l -> l.onPause(playerId, pause));
    }
}
