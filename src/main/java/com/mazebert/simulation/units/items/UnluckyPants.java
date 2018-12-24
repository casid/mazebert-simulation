package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;

public strictfp class UnluckyPants extends Item {

    public UnluckyPants() {
        super(new UnluckyPantsAbility());
    }

    @Override
    public String getName() {
        return "Unlucky Pants";
    }

    @Override
    public String getDescription() {
        return "Chances to get laid are extremely low with these pants on.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Legendary;
    }

    @Override
    public String getSinceVersion() {
        return "1.1";
    }

    @Override
    public String getIcon() {
        return "iron_pants_512";
    }

    @Override
    public int getItemLevel() {
        return 51;
    }
}
