package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;

public strictfp class LeatherBoots extends Item {
    public LeatherBoots() {
        super(new LeatherBootsAbility());
    }

    @Override
    public String getName() {
        return "Leather Boots";
    }

    @Override
    public String getDescription() {
        return "These boots smell so badly, that the carrier is always in haste.";
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
        return "0086_Cloth_Boots_512";
    }

    @Override
    public int getItemLevel() {
        return 1;
    }
}
