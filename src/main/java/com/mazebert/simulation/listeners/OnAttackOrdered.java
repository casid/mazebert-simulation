package com.mazebert.simulation.listeners;

import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.wizards.Wizard;

public strictfp class OnAttackOrdered extends ExposedSignal<OnAttackOrderedListener> {

    public void dispatch(Wizard wizard, Creep creep) {
        for (OnAttackOrderedListener listener : this) {
            listener.onAttackRequested(wizard, creep);
        }

        if (isExposed()) {
            dispatchExposed(l -> l.onAttackRequested(wizard, creep));
        }
    }
}
