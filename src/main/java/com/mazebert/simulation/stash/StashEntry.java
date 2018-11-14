package com.mazebert.simulation.stash;

import com.mazebert.simulation.Card;
import com.mazebert.simulation.CardType;

public strictfp class StashEntry<T extends Card> {
    public final CardType<T> cardType;
    public final T card;
    public int amount;

    public StashEntry(CardType<T> cardType) {
        this.cardType = cardType;
        this.card = cardType.create();
        this.amount = 1;
    }
}
