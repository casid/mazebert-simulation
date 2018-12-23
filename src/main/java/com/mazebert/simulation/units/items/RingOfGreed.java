package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;

public strictfp class RingOfGreed extends Item {

    public RingOfGreed() {
        super(new RingOfGreedAbility());
    }

    @Override
    public String getName() {
        return "Ring of greed";
    }

    @Override
    public String getDescription() {
        return "I will find you, little treasures.";
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
        return "0032_ring_512";
    }

    @Override
    public int getItemLevel() {
        return 7;
    }
}