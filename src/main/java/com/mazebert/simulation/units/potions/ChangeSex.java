package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.Rarity;

public strictfp class ChangeSex extends Potion {
    public ChangeSex() {
        super(new ChangeSexAbility());
    }

    @Override
    public String getIcon() {
        return "9012_SexPotion";
    }

    @Override
    public String getName() {
        return "Ila's Spirit of Metamorphosis";
    }

    @Override
    public String getDescription() {
        return "Explore the possibilities";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Legendary;
    }

    @Override
    public int getItemLevel() {
        return 30;
    }

    @Override
    public String getAuthor() {
        return "mazejanovic";
    }

    @Override
    public String getSinceVersion() {
        return "1.5";
    }

    @Override
    public boolean isForgeable() {
        return false;
    }

    @Override
    public boolean isBlackMarketOffer() {
        return true;
    }
}
