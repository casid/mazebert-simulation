package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class DrinkingHorn extends Item {
    public DrinkingHorn() {
        super(new DrinkingHornAbility());
    }

    @Override
    public String getName() {
        return "Roordahuizum";
    }

    @Override
    public String getDescription() {
        return "A viking's favorite mug.\nOnly equipable by vikings.";
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
        return "09_horn_512";
    }

    @Override
    public String getAuthor() {
        return "FuzzyEuk";
    }

    @Override
    public int getItemLevel() {
        return 110;
    }

    @Override
    public boolean isBlackMarketOffer() {
        return true;
    }

    @Override
    public boolean isForbiddenToEquip(Tower tower) {
        return !tower.isViking();
    }
}
