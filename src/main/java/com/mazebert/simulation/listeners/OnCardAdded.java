package com.mazebert.simulation.listeners;

import com.mazebert.simulation.CardType;

public strictfp class OnCardAdded extends ExposedSignal<OnCardAddedListener> {
    public void dispatch(CardType cardType, boolean firstEntry) {
        dispatchAll(l -> l.onCardAdded(cardType, firstEntry));
    }
}
