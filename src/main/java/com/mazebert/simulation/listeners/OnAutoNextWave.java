package com.mazebert.simulation.listeners;

public strictfp class OnAutoNextWave extends ExposedSignal<OnAutoNextWaveListener> {

    public void dispatch(int playerId, boolean onAutoNextWave) {
        dispatchAll(l -> l.onAutoNextWave(playerId, onAutoNextWave));
    }
}
