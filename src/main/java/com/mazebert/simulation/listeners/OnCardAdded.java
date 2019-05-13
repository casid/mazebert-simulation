package com.mazebert.simulation.listeners;

import com.mazebert.simulation.CardType;

public strictfp class OnCardAdded extends ExposedSignal<OnCardAddedListener> {
    public void dispatch(CardType cardType) {
        dispatchAll(l -> l.onCardAdded(cardType));
    }
}
