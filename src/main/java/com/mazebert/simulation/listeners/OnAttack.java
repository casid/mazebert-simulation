package com.mazebert.simulation.listeners;

import com.mazebert.simulation.units.creeps.Creep;

public strictfp class OnAttack extends ExposedSignal<OnAttackListener> {
    public void dispatch(Creep target) {
        for (OnAttackListener listener : this) {
            listener.onAttack(target);
        }

        if (isExposed()) {
            dispatchExposed(l -> l.onAttack(target));
        }
    }
}
