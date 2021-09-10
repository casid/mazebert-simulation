package com.mazebert.simulation.listeners;

import com.mazebert.simulation.units.items.Item;
import com.mazebert.simulation.units.wizards.Wizard;
import org.jusecase.signals.Signal;

public strictfp class OnProphecyFulfilled extends Signal<OnProphecyFulfilledListener> {
    public void dispatch(Wizard wizard, Item item) {
        for (OnProphecyFulfilledListener listener : this) {
            listener.onProphecyFulfilled(wizard, item);
        }
    }
}
