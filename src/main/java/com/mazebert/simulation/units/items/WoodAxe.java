package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;

public strictfp class WoodAxe extends Item {

    public WoodAxe() {
        super(new WoodAxeAbility());
    }

    @Override
    public String getName() {
        return "Wood Axe";
    }

    @Override
    public String getDescription() {
        return "You can cut anything with it. Wood, steaks, creeps...";
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
        return "wood_axe";
    }

    @Override
    public int getItemLevel() {
        return 11;
    }
}
