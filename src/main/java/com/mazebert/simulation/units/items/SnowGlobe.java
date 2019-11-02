package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;

public strictfp class SnowGlobe extends Item {

    @Override
    public String getName() {
        return "Snow Globe";
    }

    @Override
    public String getDescription() {
        return "A little x is in this globe.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Unique;
    }

    @Override
    public String getSinceVersion() {
        return "2.0";
    }

    @Override
    public String getIcon() {
        return "0095_One_Handed_Sworld_512"; // todo
    }

    @Override
    public int getItemLevel() {
        return 1;
    }

    @Override
    public boolean isDropable() {
        return false;
    }
}
