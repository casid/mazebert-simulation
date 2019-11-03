package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;

public strictfp class SnowGlobe extends Item {

    private String description = "There is room for a common tower in here.";

    @Override
    public String getName() {
        return "Snow Globe";
    }

    @Override
    public String getDescription() {
        return description;
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
        return "05_magic_globe_512";
    }

    @Override
    public int getItemLevel() {
        return 1;
    }

    @Override
    public boolean isDropable() {
        return false;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
