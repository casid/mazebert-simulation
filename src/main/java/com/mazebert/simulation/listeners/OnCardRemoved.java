package com.mazebert.simulation.listeners;

import com.mazebert.simulation.CardType;

public strictfp class OnCardRemoved extends ExposedSignal<OnCardRemovedListener> {
    public void dispatch(CardType cardType, int count, boolean automatic) {
        dispatchAll(l -> l.onCardRemoved(cardType, count, automatic));
    }
}
