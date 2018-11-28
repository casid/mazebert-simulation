package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.Rarity;

public class Mead extends Potion {

    public Mead() {
        super(new MeadAbility());
    }

    @Override
    public String getIcon() {
        return "9005_MeadPotion";
    }

    @Override
    public String getName() {
        return "Mead Bottle";
    }

    @Override
    public String getDescription() {
        return "A bottle of mead! It's said this is the drink for a true Viking!";
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
    public boolean isDropable() {
        return false;
    }

    @Override
    public String getSinceVersion() {
        return "0.3";
    }
}
