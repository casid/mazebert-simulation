package com.mazebert.simulation.listeners;

public strictfp class OnBonusRoundCountDown extends ExposedSignal<OnBonusRoundCountDownListener> {
    public void dispatch(int remainingSeconds) {
        dispatchAll(l -> l.onBonusRoundCountDown(remainingSeconds));
    }
}
