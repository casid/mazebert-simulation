package com.mazebert.simulation.listeners;

import com.mazebert.simulation.CardType;

import java.util.Collection;

public strictfp class OnCardsTransmuted extends ExposedSignal<OnCardsTransmutedListener> {
    public void dispatch(CardType card) {
        dispatchAll(l -> l.onCardTransmuted(card));
    }

    public void dispatch(Collection<CardType> cards) {
        dispatchAll(l -> l.onCardsTransmuted(cards));
    }

    public void dispatch() {
        dispatchAll(l -> l.onCardTransmuted(null));
    }
}
