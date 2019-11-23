package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;

public strictfp class GuardLance extends Item {

    public GuardLance() {
        super(new GuardLanceAbility());
    }

    @Override
    public String getIcon() {
        return "0094_One_Handed_Hammer_512"; // TODO
    }

    @Override
    public String getName() {
        return "Guard Lance";
    }

    @Override
    public String getDescription() {
        return "Great to lean against.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Uncommon;
    }

    @Override
    public int getItemLevel() {
        return 21;
    }

    @Override
    public String getSinceVersion() {
        return "2.0";
    }
}
