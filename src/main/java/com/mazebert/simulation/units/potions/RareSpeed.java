package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.Rarity;

public strictfp class RareSpeed extends Potion {
    public RareSpeed() {
        super(new RareSpeedAbility());
    }

    @Override
    public String getIcon() {
        return "9001_SpeedPotion";
    }

    @Override
    public String getName() {
        return "Great Potion of Speed";
    }

    @Override
    public String getDescription() {
        return "If you fall behind, run faster.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Rare;
    }

    @Override
    public int getItemLevel() {
        return 24;
    }

    @Override
    public String getSinceVersion() {
        return "0.3";
    }
}
