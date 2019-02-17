package com.mazebert.simulation.listeners;

import org.jusecase.signals.Signal;

public strictfp class OnAllPotionsConsumed extends Signal<OnAllPotionsConsumedListener> {
    public void dispatch() {
        for (OnAllPotionsConsumedListener listener : this) {
            listener.onAllPotionsConsumed();
        }
    }
}
