package com.mazebert.simulation.listeners;

import com.mazebert.simulation.units.abilities.Ability;

public strictfp class OnAbilityRemoved extends ExposedSignal<OnAbilityRemovedListener> {
    public void dispatch(Ability ability) {
        for (OnAbilityRemovedListener listener : this) {
            listener.onAbilityRemoved(ability);
        }

        if (isExposed()) {
            dispatchExposed(l -> l.onAbilityRemoved(ability));
        }
    }
}
