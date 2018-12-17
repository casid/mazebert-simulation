package com.mazebert.simulation.stash;

import com.mazebert.simulation.Card;
import com.mazebert.simulation.CardType;
import com.mazebert.simulation.hash.Hash;
import com.mazebert.simulation.hash.Hashable;

public strictfp class StashEntry<T extends Card> implements ReadonlyStashEntry<T>, Hashable {
    public final CardType<T> cardType;
    public final T card;
    public int amount;

    public StashEntry(CardType<T> cardType) {
        this(cardType, cardType.instance());
    }

    public StashEntry(CardType<T> cardType, T card) {
        this.cardType = cardType;
        this.card = card;
        this.amount = 1;
    }

    @Override
    public void hash(Hash hash) {
        hash.add(cardType);
        hash.add(amount);
    }

    @Override
    public CardType<T> getCardType() {
        return cardType;
    }

    @Override
    public T getCard() {
        return card;
    }

    @Override
    public int getAmount() {
        return amount;
    }
}
