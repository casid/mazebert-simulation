package com.mazebert.simulation.listeners;

public strictfp class OnTimeLordCountDown extends ExposedSignal<OnTimeLordCountDownListener> {
    public void dispatch(int remainingSeconds) {
        dispatchAll(l -> l.onTimeLordCountDown(remainingSeconds));
    }
}
