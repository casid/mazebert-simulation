package com.mazebert.simulation.listeners;

import com.mazebert.simulation.units.wizards.Wizard;

public strictfp class OnGoldChanged extends ExposedSignal<OnGoldChangedListener> {
    public void dispatch(Wizard wizard, long oldGold, long newGold) {
        for (OnGoldChangedListener listener : this) {
            listener.onGoldChanged(wizard, oldGold, newGold);
        }

        if (isExposed()) {
            dispatchExposed(l -> l.onGoldChanged(wizard, oldGold, newGold));
        }
    }
}
