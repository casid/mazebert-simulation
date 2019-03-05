package com.mazebert.simulation.units.quests;

import com.mazebert.simulation.CardType;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.listeners.OnCardsTransmutedListener;
import com.mazebert.simulation.units.items.ItemType;
import com.mazebert.simulation.units.wizards.Wizard;

import java.util.Collection;

public strictfp class TransmuteStackQuest extends Quest implements OnCardsTransmutedListener {
    public TransmuteStackQuest() {
        super(QuestReward.Small, 1);
    }

    @Override
    protected void initialize(Wizard unit) {
        super.initialize(unit);
        unit.itemStash.add(ItemType.TransmuteStack);
        unit.itemStash.add(ItemType.TransmuteStack);
        unit.itemStash.add(ItemType.TransmuteStack);
        unit.itemStash.add(ItemType.TransmuteStack);

        unit.onCardsTransmuted.add(this);
    }

    @Override
    protected void dispose(Wizard unit) {
        unit.onCardsTransmuted.remove(this);
        super.dispose(unit);
    }

    @Override
    public void onCardTransmuted(Rarity rarity, CardType cardType) {
        // will not happen
    }

    @Override
    public void onCardsTransmuted(Rarity rarity, Collection<CardType> cardType, int transmutedCards) {
        if (transmutedCards == 4) {
            addAmount(1);
        }
    }

    @Override
    public boolean isHidden() {
        return true;
    }

    @Override
    public boolean isAllowedInTutorial() {
        return false;
    }

    @Override
    public String getSinceVersion() {
        return "1.3.0";
    }

    @Override
    public String getTitle() {
        return "About time!";
    }

    @Override
    public String getDescription() {
        return "No longer swipe until\nyour fingers bleed.";
    }
}
