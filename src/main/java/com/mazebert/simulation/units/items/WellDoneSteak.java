package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;

public strictfp class WellDoneSteak extends Item {

    public WellDoneSteak() {
        super(new WellDoneSteakAbility());
    }

    @Override
    public String getName() {
        return "Well done T-Bone Steak";
    }

    @Override
    public String getDescription() {
        return "This steak is totally cooked through and tough as leather. Food for the brave.";
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
        return "0063_meat_512";
    }

    @Override
    public int getItemLevel() {
        return 11;
    }
}
