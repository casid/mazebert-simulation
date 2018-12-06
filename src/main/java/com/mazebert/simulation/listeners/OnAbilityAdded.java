package com.mazebert.simulation.listeners;

import com.mazebert.simulation.units.abilities.Ability;

public strictfp class OnAbilityAdded extends ExposedSignal<OnAbilityAddedListener> {
    public void dispatch(Ability ability) {
        for (OnAbilityAddedListener listener : this) {
            listener.onAbilityAdded(ability);
        }

        if (isExposed()) {
            dispatchExposed(l -> l.onAbilityAdded(ability));
        }
    }
}
