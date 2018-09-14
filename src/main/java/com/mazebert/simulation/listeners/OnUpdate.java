package com.mazebert.simulation.listeners;

import org.jusecase.signals.Signal;

public strictfp class OnUpdate extends Signal<OnUpdateListener> {
    public OnUpdate() {
        super(OnUpdateListener.class);
    }

    public void dispatch(float dt) {
        for (int i = 0; i < size; ++i) {
            listeners[i].onUpdate(dt);
        }
    }
}
