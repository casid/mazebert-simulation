package com.mazebert.simulation.units.quests;

import com.mazebert.simulation.CardType;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.listeners.OnCardsTransmutedListener;
import com.mazebert.simulation.units.items.ItemType;
import com.mazebert.simulation.units.potions.CardDust;
import com.mazebert.simulation.units.wizards.Wizard;

import java.util.Collection;

public strictfp class TransmuteUniquesQuest extends Quest implements OnCardsTransmutedListener {
    public TransmuteUniquesQuest() {
        super(QuestReward.Small, 1);
    }

    @Override
    protected void initialize(Wizard unit) {
        super.initialize(unit);
        unit.itemStash.add(ItemType.TransmuteUniques);
        unit.itemStash.add(ItemType.TransmuteUniques);

        unit.onCardsTransmuted.add(this);
    }

    @Override
    protected void dispose(Wizard unit) {
        unit.onCardsTransmuted.remove(this);
        super.dispose(unit);
    }

    @Override
    public void onCardTransmuted(Rarity rarity, CardType cardType, boolean automatic) {
        checkProgress(cardType);
    }

    @Override
    public void onCardsTransmuted(Rarity rarity, Collection<CardType> cardTypes, int transmutedCards) {
        if (cardTypes != null) {
            for (CardType cardType : cardTypes) {
                checkProgress(cardType);
            }
        }
    }

    private void checkProgress(CardType cardType) {
        if (cardType != null && cardType.instance() instanceof CardDust) {
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
        return "Dust, everywhere!";
    }

    @Override
    public String getDescription() {
        return "You just created your first\nCard Dust.";
    }
}
