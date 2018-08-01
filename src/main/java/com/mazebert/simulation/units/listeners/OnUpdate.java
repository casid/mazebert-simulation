package com.mazebert.simulation.units.listeners;

import org.jusecase.signals.Signal;

public strictfp class OnUpdate extends Signal<OnUpdateListener> {
    public void dispatch(float dt) {
        for (OnUpdateListener listener : getListeners()) {
            listener.onUpdate(dt);
        }
    }
}
