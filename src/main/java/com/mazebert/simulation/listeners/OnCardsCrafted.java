package com.mazebert.simulation.listeners;

import com.mazebert.simulation.CardType;
import com.mazebert.simulation.units.wizards.Wizard;

import java.util.Collection;

public strictfp class OnCardsCrafted extends ExposedSignal<OnCardsCraftedListener> {
    public void dispatch(Wizard wizard, CardType card) {
        dispatchAll(l -> l.onCardCrafted(wizard, card));
    }

    public void dispatch(Wizard wizard, Collection<CardType> cards) {
        dispatchAll(l -> l.onCardsCrafted(wizard, cards));
    }

    public void dispatch(Wizard wizard) {
        dispatchAll(l -> l.onCardCrafted(wizard, null));
    }
}
