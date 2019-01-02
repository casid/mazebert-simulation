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
        return "Potion of sex change";
    }

    @Override
    public String getDescription() {
        return "Use at your own risk.";
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
}
