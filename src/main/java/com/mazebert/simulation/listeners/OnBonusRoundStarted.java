package com.mazebert.simulation.listeners;

public strictfp class OnBonusRoundStarted extends ExposedSignal<OnBonusRoundStartedListener> {
    public void dispatch() {
        dispatchAll(OnBonusRoundStartedListener::onBonusRoundStarted);
    }
}
