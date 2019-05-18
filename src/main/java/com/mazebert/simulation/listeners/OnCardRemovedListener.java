package com.mazebert.simulation.listeners;

import com.mazebert.simulation.CardType;

public interface OnCardRemovedListener {
    void onCardRemoved(CardType cardType, int count, boolean automatic);
}
