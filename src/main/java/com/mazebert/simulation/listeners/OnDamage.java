package com.mazebert.simulation.listeners;

import com.mazebert.simulation.units.creeps.Creep;
import org.jusecase.signals.Signal;

public strictfp class OnDamage extends Signal<OnDamageListener> {
    public OnDamage() {
        super(OnDamageListener.class);
    }

    public void dispatch(Creep target, double damage) {
        for (int i = 0; i < size; ++i) {
            listeners[i].onDamage(target, damage);
        }
    }
}
