package com.mazebert.simulation.listeners;

import com.mazebert.simulation.units.creeps.Creep;
import org.jusecase.signals.Signal;

public strictfp class OnDamage extends Signal<OnDamageListener> {
   public void dispatch(Object origin, Creep target, double damage, int multicrits) {
        for (OnDamageListener listener : this) {
            listener.onDamage(origin, target, damage, multicrits);
        }
    }
}
