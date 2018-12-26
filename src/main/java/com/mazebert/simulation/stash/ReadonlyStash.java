package com.mazebert.simulation.stash;

import com.mazebert.simulation.Card;
import com.mazebert.simulation.CardCategory;
import com.mazebert.simulation.CardType;

public interface ReadonlyStash<T extends Card> {
    int size();
    ReadonlyStashEntry<T> get(int index);
    ReadonlyStashEntry<T> get(CardType<T> cardType);
    int getIndex(CardType<T> cardType);
    CardCategory getCardCategory();

    @SuppressWarnings("unused") // transient value for client
    int getLastViewedIndex();

    @SuppressWarnings("unused") // transient value for client
    void setLastViewedIndex(int index);
}
