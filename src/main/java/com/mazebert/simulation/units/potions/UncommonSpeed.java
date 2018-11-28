package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.Rarity;

public strictfp class UncommonSpeed extends Potion {
    public UncommonSpeed() {
        super(new UncommonSpeedAbility());
    }

    @Override
    public String getIcon() {
        return "9001_SpeedPotion";
    }

    @Override
    public String getName() {
        return "Potion of Speed";
    }

    @Override
    public String getDescription() {
        return "If you fall behind, run faster.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Uncommon;
    }

    @Override
    public int getItemLevel() {
        return 8;
    }

    @Override
    public String getSinceVersion() {
        return "0.3";
    }
}
