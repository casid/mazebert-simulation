package com.mazebert.simulation.units.quests;

import com.mazebert.simulation.CardType;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.listeners.OnCardsTransmutedListener;
import com.mazebert.simulation.units.wizards.Wizard;

import java.util.Collection;

public strictfp class TransmuteCardsQuest extends Quest implements OnCardsTransmutedListener {
    public TransmuteCardsQuest() {
        super(QuestReward.Medium, 200);
    }

    @Override
    protected void initialize(Wizard unit) {
        super.initialize(unit);
        unit.onCardsTransmuted.add(this);
    }

    @Override
    protected void dispose(Wizard unit) {
        unit.onCardsTransmuted.remove(this);
        super.dispose(unit);
    }

    @Override
    public void onCardTransmuted(Rarity rarity, CardType cardType, boolean automatic) {
        addAmount(1);
    }

    @Override
    public void onCardsTransmuted(Rarity rarity, Collection<CardType> cardType, int transmutedCards) {
        addAmount(transmutedCards);
    }

    @Override
    public String getSinceVersion() {
        return "1.3.0";
    }

    @Override
    public String getTitle() {
        return "Deck Shuffler";
    }

    @Override
    public String getDescription() {
        return "Transmute " + requiredAmount + " cards!";
    }
}
