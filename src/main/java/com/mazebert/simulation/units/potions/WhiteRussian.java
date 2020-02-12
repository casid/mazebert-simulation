package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;

public strictfp class WhiteRussian extends Potion {
    public WhiteRussian() {
        super(new WhiteRussianAbility());
    }

    @Override
    public String getIcon() {
        return "white_russian";
    }

    @Override
    public String getName() {
        return "White Russian";
    }

    @Override
    public String getDescription() {
        return "Vodka, Kahlua, and Cream.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Legendary;
    }

    @Override
    public int getItemLevel() {
        return 1;
    }

    @Override
    public String getSinceVersion() {
        return "2.0";
    }

    @Override
    public boolean isForgeable() {
        return false;
    }

    @Override
    public boolean isSupporterReward() {
        return true;
    }

    @Override
    public boolean isTradingAllowed() {
        return false;
    }

    @Override
    public Element getElement() {
        return Element.Light;
    }
}
