package com.mazebert.simulation.listeners;

import com.mazebert.simulation.units.Unit;
import org.jusecase.signals.Signal;

public strictfp class OnHealthChanged extends Signal<OnHealthChangedListener> {
    public void dispatch(Unit unit, double oldHealth, double newHealth) {
        for (OnHealthChangedListener listener : this) {
            listener.onHealthChanged(unit, oldHealth, newHealth);
        }
    }
}
