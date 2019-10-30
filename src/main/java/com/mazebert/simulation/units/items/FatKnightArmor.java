package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;

public strictfp class FatKnightArmor extends Item {

    public FatKnightArmor() {
        super(new FatKnightArmorAbility());
    }

    @Override
    public String getName() {
        return "Armor of a fat knight";
    }

    @Override
    public String getDescription() {
        return "This armor is so big that it fits 9 towers.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Legendary;
    }

    @Override
    public String getSinceVersion() {
        return "2.0";
    }

    @Override
    public String getIcon() {
        return "0009_cuirass_512";
    }

    @Override
    public int getItemLevel() {
        return 120;
    }

    @Override
    public boolean isBlackMarketOffer() {
        return true;
    }

    @Override
    public String getAuthor() {
        return "Ghul";
    }
}
