package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;

public strictfp class Trident extends Item {

    public Trident() {
        super(new TridentAbility());
    }

    @Override
    public String getName() {
        return "Trident of Poseidon";
    }

    @Override
    public String getDescription() {
        return "Crafted by the three Cyclopes.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Legendary;
    }

    @Override
    public String getSinceVersion() {
        return "1.5";
    }

    @Override
    public String getIcon() {
        return "0056_throw_512";
    }

    @Override
    public int getItemLevel() {
        return 87;
    }

    @Override
    public boolean isBlackMarketOffer() {
        return true;
    }
}
