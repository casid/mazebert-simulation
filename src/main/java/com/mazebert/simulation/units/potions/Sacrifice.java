package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.Rarity;

public strictfp class Sacrifice extends Potion {
    public Sacrifice() {
        super(new SacrificeAbility());
    }

    @Override
    public String getIcon() {
        return "9008_SacrificePotion";
    }

    @Override
    public String getName() {
        return "Soul Flask";
    }

    @Override
    public String getDescription() {
        return "Sometimes it's necessary to sacrifice an individual for the bigger picture.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Unique;
    }

    @Override
    public int getItemLevel() {
        return 56;
    }

    @Override
    public String getAuthor() {
        return "Robert Ruehlmann";
    }

    @Override
    public String getSinceVersion() {
        return "0.6";
    }

    @Override
    public boolean isDestructive() {
        return true;
    }
}
