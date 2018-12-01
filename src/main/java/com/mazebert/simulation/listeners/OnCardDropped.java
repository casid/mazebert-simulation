package com.mazebert.simulation.listeners;

import com.mazebert.simulation.Card;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.wizards.Wizard;
import org.jusecase.signals.Signal;

public strictfp class OnCardDropped extends Signal<OnCardDroppedListener> {
    public OnCardDropped() {
        super(OnCardDroppedListener.class);
    }

    public void dispatch(Wizard wizard, Creep creep, Card card) {
        for (int i = 0; i < size; ++i) {
            listeners[i].onCardDropped(wizard, creep, card);
        }
    }
}
