package com.mazebert.simulation.listeners;

import org.jusecase.signals.Signal;

public strictfp class OnUpdate extends Signal<OnUpdateListener> {
    public void dispatch(float dt) {
        for (OnUpdateListener listener : this) {
            listener.onUpdate(dt);
        }
    }
}
