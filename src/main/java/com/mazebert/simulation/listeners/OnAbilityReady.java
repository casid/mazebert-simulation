package com.mazebert.simulation.listeners;

import com.mazebert.simulation.units.abilities.ActiveAbility;

public strictfp class OnAbilityReady extends ExposedSignal<OnAbilityReadyListener> {
    public void dispatch(ActiveAbility ability) {
        for (OnAbilityReadyListener listener : this) {
            listener.onAbilityReady(ability);
        }

        if (isExposed()) {
            dispatchExposed(l -> l.onAbilityReady(ability));
        }
    }
}
