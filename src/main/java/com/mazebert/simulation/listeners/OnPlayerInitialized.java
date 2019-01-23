package com.mazebert.simulation.listeners;

public strictfp class OnPlayerInitialized extends ExposedSignal<OnPlayerInitializedListener> {
    public void dispatch(int playerId) {
        dispatchAll(l -> l.onPlayerInitialized(playerId));
    }
}
