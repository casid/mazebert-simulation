package com.mazebert.simulation.listeners;

import com.mazebert.simulation.CardType;
import com.mazebert.simulation.Rarity;

import java.util.Collection;

public interface OnCardsTransmutedListener {
    void onCardTransmuted(Rarity rarity, CardType cardType, boolean automatic);

    void onCardsTransmuted(Rarity rarity, Collection<CardType> cardType, int transmutedCards);
}
