package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;

public strictfp class Lightbringer extends Item {

    public Lightbringer() {
        super(new LightbringerHealAbility(), new LightbringerRemoveAbility());
    }

    @Override
    public String getName() {
        return "Lightbringer";
    }

    @Override
    public String getDescription() {
        return "Lucifer's sword";
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
        return "blood_demon_blade_512"; // TODO
    }

    @Override
    public int getItemLevel() {
        return 1;
    }

    @Override
    public boolean isDropable() {
        return false;
    }

    @Override
    public boolean isForgeable() {
        return false;
    }

    @Override
    public boolean isLight() {
        return true;
    }
}