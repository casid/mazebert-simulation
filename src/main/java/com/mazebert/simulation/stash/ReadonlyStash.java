package com.mazebert.simulation.stash;

import com.mazebert.simulation.Card;
import com.mazebert.simulation.CardCategory;
import com.mazebert.simulation.CardType;
import com.mazebert.simulation.listeners.OnCardAdded;
import com.mazebert.simulation.listeners.OnCardRemoved;

public interface ReadonlyStash<T extends Card> {
    int size();
    ReadonlyStashEntry<T> get(int index);
    ReadonlyStashEntry<T> get(CardType<T> cardType);
    int getIndex(CardType<T> cardType);

    @SuppressWarnings("unused") // for client
    CardCategory getCardCategory();

    @SuppressWarnings("unused") // for client
    OnCardAdded onCardAdded();
    OnCardRemoved onCardRemoved();

    int getTransmutedCommons();
    int getTransmutedUncommons();
    int getTransmutedRares();

    @SuppressWarnings("unused") // transient value for client
    int getLastViewedIndex();

    @SuppressWarnings("unused") // transient value for client
    void setLastViewedIndex(int index);
}
