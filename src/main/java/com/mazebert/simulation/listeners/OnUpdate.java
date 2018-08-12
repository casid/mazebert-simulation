package com.mazebert.simulation.listeners;

import org.jusecase.signals.Signal;

public strictfp class OnUpdate extends Signal<OnUpdateListener> {
    public void dispatch(float dt) {
        if (size() > 0) {
            for (OnUpdateListener listener : getListeners()) {
                listener.onUpdate(dt);
            }
        }
    }
}
