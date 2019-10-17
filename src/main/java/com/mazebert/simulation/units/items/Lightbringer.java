package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;

public strictfp class Lightbringer extends Item {

    public Lightbringer() {
        super();
    }

    @Override
    public String getName() {
        return "Lightbringer";
    }

    @Override
    public String getDescription() {
        return "Sword of Lucifer, the angel";
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
        return "blood_demon_blade_512"; // TODO
    }

    @Override
    public int getItemLevel() {
        return 1;
    }

    @Override
    public boolean isDropable() {
        return false;
    }

    @Override
    public boolean isLight() {
        return true;
    }

    @Override
    public boolean isForgeable() {
        return false;
    }
}
