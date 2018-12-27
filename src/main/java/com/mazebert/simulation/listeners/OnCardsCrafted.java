package com.mazebert.simulation.listeners;

import com.mazebert.simulation.CardType;
import com.mazebert.simulation.units.wizards.Wizard;

import java.util.Collection;

public strictfp class OnCardsCrafted extends ExposedSignal<OnCardsCraftedListener> {
    public void dispatch(CardType card) {
        dispatchAll(l -> l.onCardCrafted(card));
    }

    public void dispatch(Collection<CardType> cards) {
        dispatchAll(l -> l.onCardsCrafted(cards));
    }

    public void dispatch() {
        dispatchAll(l -> l.onCardCrafted(null));
    }
}
