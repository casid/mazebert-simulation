package com.mazebert.simulation.listeners;

public strictfp class OnBonusRoundFinished extends ExposedSignal<OnBonusRoundFinishedListener> {
    public void dispatch() {
        dispatchAll(OnBonusRoundFinishedListener::onBonusRoundFinished);
    }
}
