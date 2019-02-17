package com.mazebert.simulation.listeners;

import com.mazebert.simulation.CardType;
import com.mazebert.simulation.Rarity;

import java.util.Collection;

public strictfp class OnCardsTransmuted extends ExposedSignal<OnCardsTransmutedListener> {
    public void dispatch(Rarity rarity, Collection<CardType> cards, int transmutedCards) {
        dispatchAll(l -> l.onCardsTransmuted(rarity, cards, transmutedCards));
    }

    public void dispatch(Rarity rarity, CardType card) {
        dispatchAll(l -> l.onCardTransmuted(rarity, card));
    }
}
