package com.mazebert.simulation.units.wizards;

import com.mazebert.simulation.Card;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.towers.Tower;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public strictfp class Wizard extends Unit {
    private final List<Card> hand = new ArrayList<>();
    private final List<Card> pile = new ArrayList<>();

    public List<Card> getHand() {
        return hand;
    }

    public void addCardToHand(Card card) {
        hand.add(card);
    }

    public Card removeCardFromHand(UUID cardId) {
        int index = getCardIndexOnHand(cardId);
        if (index >= 0) {
            return hand.remove(index);
        }
        return null;
    }

    private int getCardIndexOnHand(UUID cardId) {
        int index = 0;
        for (Card card : hand) {
            if (card.getCardId().equals(cardId)) {
                return index;
            }
            ++index;
        }
        return -1;
    }

    @Override
    public String getModelId() {
        return null;
    }
}
