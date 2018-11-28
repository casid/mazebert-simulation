package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.Rarity;

public strictfp class RareDrops extends Potion {
    public RareDrops() {
        super(new RareDropsAbility());
    }

    @Override
    public String getIcon() {
        return "9004_GreedPotion";
    }

    @Override
    public String getName() {
        return "Water of Life";
    }

    @Override
    public String getDescription() {
        return "This water is pure gold. Taste it, and it will tell you stories about long forgotten legends!";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Rare;
    }

    @Override
    public int getItemLevel() {
        return 32;
    }

    @Override
    public String getSinceVersion() {
        return "0.3";
    }
}
