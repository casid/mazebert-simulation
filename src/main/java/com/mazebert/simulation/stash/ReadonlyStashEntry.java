package com.mazebert.simulation.stash;

import com.mazebert.simulation.Card;
import com.mazebert.simulation.CardType;

public interface ReadonlyStashEntry<T extends Card> {
    CardType<T> getCardType();
    T getCard();
    int getAmount();
}
