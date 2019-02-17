package com.mazebert.simulation.stash;

import com.mazebert.simulation.CardCategory;
import com.mazebert.simulation.listeners.OnAllPotionsConsumed;
import com.mazebert.simulation.listeners.OnCardRemoved;
import com.mazebert.simulation.units.potions.Potion;
import com.mazebert.simulation.units.potions.PotionType;

import java.util.EnumMap;

public strictfp class PotionStash extends Stash<Potion> {
    public final OnAllPotionsConsumed onAllPotionsConsumed = new OnAllPotionsConsumed();

    @SuppressWarnings("unchecked")
    public PotionStash() {
        super(new EnumMap(PotionType.class), new EnumMap(PotionType.class));
    }

    @Override
    protected PotionType[] getPossibleDrops() {
        return PotionType.values();
    }

    @Override
    public CardCategory getCardCategory() {
        return CardCategory.Potion;
    }
}
