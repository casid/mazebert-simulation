package com.mazebert.simulation.listeners;

import com.mazebert.simulation.units.abilities.ActiveAbility;

public strictfp class OnAbilityActivated extends ExposedSignal<OnAbilityActivatedListener> {
    public void dispatch(ActiveAbility ability) {
        for (OnAbilityActivatedListener listener : this) {
            listener.onAbilityActivated(ability);
        }

        if (isExposed()) {
            dispatchExposed(l -> l.onAbilityActivated(ability));
        }
    }
}
