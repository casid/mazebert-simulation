package com.mazebert.simulation.listeners;

import com.mazebert.simulation.units.wizards.Wizard;

public strictfp class OnRemainingCardTransfersChanged extends ExposedSignal<OnRemainingCardTransfersChangedListener> {

    public void dispatch(Wizard wizard) {
        dispatchAll(l -> l.onRemainingCardTransfersChanged(wizard));
    }
}
