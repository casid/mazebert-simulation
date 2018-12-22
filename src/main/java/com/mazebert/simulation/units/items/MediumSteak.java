package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;

public strictfp class MediumSteak extends Item {

    public MediumSteak() {
        super(new MediumSteakAbility());
    }

    @Override
    public String getName() {
        return "Medium T-Bone Steak";
    }

    @Override
    public String getDescription() {
        return "This medium steak is tender and juicy. Food for the gourmets.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Uncommon;
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
        return 22;
    }
}
