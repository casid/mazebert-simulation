package com.mazebert.simulation.listeners;

import com.mazebert.simulation.units.wizards.Wizard;

public strictfp class OnElementResearchPointsChanged extends ExposedSignal<OnElementResearchPointsChangedListener> {

    public void dispatch(Wizard wizard) {
        dispatchAll(l -> l.onElementResearchPointsChanged(wizard));
    }
}
