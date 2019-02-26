package com.mazebert.simulation.tutorial;

import com.mazebert.simulation.CardType;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.listeners.OnCardsTransmutedListener;
import com.mazebert.simulation.units.items.ItemType;
import com.mazebert.simulation.units.wizards.Wizard;

import java.util.Collection;

public strictfp class Lesson15TransmuteItems extends Lesson implements OnCardsTransmutedListener {

    @Override
    public void initialize(Wizard wizard) {
        super.initialize(wizard);
        wizard.itemStash.add(ItemType.Pumpkin);
        wizard.itemStash.add(ItemType.Pumpkin);
        wizard.itemStash.add(ItemType.Pumpkin);
        wizard.itemStash.add(ItemType.Pumpkin);

        wizard.onCardsTransmuted.add(this);

        pauseGame();
    }

    @Override
    public void dispose(Wizard wizard) {
        wizard.onCardsTransmuted.remove(this);
        super.dispose(wizard);
    }

    @Override
    public void onCardTransmuted(Rarity rarity, CardType cardType) {
        if (cardType != null) {
            finish();
        }
    }

    @Override
    public void onCardsTransmuted(Rarity rarity, Collection<CardType> cardType, int transmutedCards) {
        if (cardType != null && !cardType.isEmpty()) {
            finish();
        }
    }
}
