package com.mazebert.simulation.listeners;

import com.mazebert.simulation.units.creeps.Creep;
import org.jusecase.signals.Signal;

public strictfp class OnHealthChanged extends Signal<OnHealthChangedListener> {
    public void dispatch(Creep creep, double oldHealth, double newHealth) {
        for (OnHealthChangedListener listener : this) {
            listener.onHealthChanged(creep, oldHealth, newHealth);
        }
    }
}
