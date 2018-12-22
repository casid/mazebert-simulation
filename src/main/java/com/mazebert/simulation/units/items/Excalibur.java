package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;

public strictfp class Excalibur extends Item {

    public Excalibur() {
        super(new ExcaliburAbility());
    }

    @Override
    public String getName() {
        return "Excalibur";
    }

    @Override
    public String getDescription() {
        return "King Arthur's legendary sword. It slices through iron as through wood.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Unique;
    }

    @Override
    public String getSinceVersion() {
        return "0.2";
    }

    @Override
    public String getIcon() {
        return "0095_One_Handed_Sworld_512";
    }

    @Override
    public int getItemLevel() {
        return 80;
    }
}
