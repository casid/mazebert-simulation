package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;

public strictfp class WetTowel extends Item {

    public WetTowel() {
        super(new WetTowelAbility());
    }

    @Override
    public String getName() {
        return "Wet Towel";
    }

    @Override
    public String getDescription() {
        return "All creeps fear being slapped by a wet towel!";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Common;
    }

    @Override
    public String getSinceVersion() {
        return "0.2";
    }

    @Override
    public String getIcon() {
        return "0021_cloth_512";
    }

    @Override
    public int getItemLevel() {
        return 5;
    }
}
