package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.Rarity;

public strictfp class CommonSpeed extends Potion {
    public CommonSpeed() {
        super(new CommonSpeedAbility());
    }

    @Override
    public String getIcon() {
        return "9001_SpeedPotion";
    }

    @Override
    public String getName() {
        return "Small Potion of Speed";
    }

    @Override
    public String getDescription() {
        return "If you fall behind, run faster.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Common;
    }

    @Override
    public int getItemLevel() {
        return 1;
    }

    @Override
    public String getSinceVersion() {
        return "0.3";
    }
}
