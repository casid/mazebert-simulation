package com.mazebert.simulation.listeners;

public strictfp class OnLoadingProgress extends ExposedSignal<OnLoadingProgressListener> {

    public void dispatch(int playerId, int progress) {
        dispatchAll(l -> l.onLoadingProgress(playerId, progress));
    }
}
