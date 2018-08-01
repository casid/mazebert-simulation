package com.mazebert.simulation.units.listeners;

import com.mazebert.simulation.units.Unit;
import org.jusecase.signals.Signal;

public strictfp class OnAttack extends Signal<OnAttackListener> {
    public void dispatch(Unit unit) {
        for (OnAttackListener listener : getListeners()) {
            listener.onAttack(unit);
        }
    }
}
