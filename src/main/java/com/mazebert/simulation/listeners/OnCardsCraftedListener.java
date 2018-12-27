package com.mazebert.simulation.listeners;

import com.mazebert.simulation.CardType;
import com.mazebert.simulation.units.wizards.Wizard;

import java.util.Collection;

public interface OnCardsCraftedListener {
    void onCardCrafted(CardType cardType);

    void onCardsCrafted(Collection<CardType> cardType);
}