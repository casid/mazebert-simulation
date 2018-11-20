package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;

public strictfp class BabySword extends Item {

    public BabySword() {
        super(new BabySwordAbility());
    }

    @Override
    public String getName() {
        return "Baby Sword";
    }

    @Override
    public String getDescription() {
        return "Alright, it's not the largest sword but size doesn't really matter, huh?";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Common;
    }

    @Override
    public String getSinceVersion() {
        return "0.2";
    }
}
