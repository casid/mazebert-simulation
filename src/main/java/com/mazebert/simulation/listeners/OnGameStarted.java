package com.mazebert.simulation.listeners;

import org.jusecase.signals.Signal;

public strictfp class OnGameStarted extends Signal<OnGameStartedListener> {
   public void dispatch() {
       for (OnGameStartedListener listener : this) {
           listener.onGameStarted();
       }
    }
}
