package com.mazebert.simulation.listeners;

import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.towers.Tower;
import org.jusecase.signals.Signal;

public strictfp class OnCreepHealthChanged extends Signal<OnCreepHealthChangedListener> {
    public void dispatch(Tower tower, Creep creep, double oldHealth, double newHealth) {
        for (OnCreepHealthChangedListener listener : this) {
            listener.onHealthChanged(tower, creep, oldHealth, newHealth);
        }
    }
}
