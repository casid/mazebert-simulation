package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;

public strictfp class Mjoelnir extends Item {

    public Mjoelnir() {
        super(new MjoelnirAbility());
    }

    @Override
    public String getName() {
        return "Mjoelnir";
    }

    @Override
    public String getDescription() {
        return "Hammer of Thor.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Legendary;
    }

    @Override
    public String getSinceVersion() {
        return "1.3";
    }

    @Override
    public String getIcon() {
        return "mjoelnir-512";
    }

    @Override
    public int getItemLevel() {
        return 100;
    }

    @Override
    public String getAuthor() {
        return "Quofum";
    }

    @Override
    public boolean isBlackMarketOffer() {
        return true;
    }
}
