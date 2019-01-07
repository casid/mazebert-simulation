package com.mazebert.simulation.listeners;

public strictfp class OnBonusRoundSurvived extends ExposedSignal<OnBonusRoundSurvivedListener> {
    public void dispatch(int seconds) {
        dispatchAll(l -> l.onBonusRoundSurvived(seconds));
    }
}
