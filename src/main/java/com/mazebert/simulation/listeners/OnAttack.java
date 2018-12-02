package com.mazebert.simulation.listeners;

import com.mazebert.simulation.units.creeps.Creep;
import org.jusecase.signals.Signal;

public strictfp class OnAttack extends Signal<OnAttackListener> {
    public void dispatch(Creep target) {
        for (OnAttackListener listener : this) {
            listener.onAttack(target);
        }
    }
}
