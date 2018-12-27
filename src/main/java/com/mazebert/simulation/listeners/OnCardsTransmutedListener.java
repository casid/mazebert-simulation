package com.mazebert.simulation.listeners;

import com.mazebert.simulation.CardType;
import com.mazebert.simulation.units.wizards.Wizard;

import java.util.Collection;

public interface OnCardsTransmutedListener {
    void onCardTransmuted(CardType cardType);

    void onCardsTransmuted(Collection<CardType> cardType);
}
