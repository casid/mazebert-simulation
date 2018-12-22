package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;

public strictfp class GoldCoins extends Item {

    public GoldCoins() {
        super(new GoldCoinsAbility());
    }

    @Override
    public String getName() {
        return "Gold coins";
    }

    @Override
    public String getDescription() {
        return "Beware the attraction of money! It's the first law of the universe.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Common;
    }

    @Override
    public String getSinceVersion() {
        return "0.2";
    }

    @Override
    public String getIcon() {
        return "0061_money_512";
    }

    @Override
    public int getItemLevel() {
        return 5;
    }
}
