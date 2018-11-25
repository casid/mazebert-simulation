package com.mazebert.simulation.listeners;

import com.mazebert.simulation.units.creeps.Creep;
import org.jusecase.signals.Signal;

public strictfp class OnHealthChanged extends Signal<OnHealthChangedListener> {
    public OnHealthChanged() {
        super(OnHealthChangedListener.class);
    }

    public void dispatch(Creep creep, double oldHealth, double newHealth) {
        for (int i = 0; i < size; ++i) {
            listeners[i].onHealthChanged(creep, oldHealth, newHealth);
        }
    }
}
