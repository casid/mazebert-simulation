package com.mazebert.simulation.listeners;

import com.mazebert.simulation.CardType;

public interface OnCardAddedListener {
    void onCardAdded(CardType cardType, boolean firstEntry);
}
