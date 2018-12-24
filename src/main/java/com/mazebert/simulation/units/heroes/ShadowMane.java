package com.mazebert.simulation.units.heroes;

import com.mazebert.simulation.Rarity;

public strictfp class ShadowMane extends Hero {

    public ShadowMane() {
        addAbility(new ShadowManeAbility());
    }

    @Override
    public String getName() {
        return "Shadow Mane";
    }

    @Override
    public String getDescription() {
        return "The Lord of all horses,\none of the Mearas.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Common;
    }

    @Override
    public String getIcon() {
        return "shadowmane_512";
    }

    @Override
    public int getItemLevel() {
        return 1;
    }

    @Override
    public String getSinceVersion() {
        return "1.0";
    }
}
