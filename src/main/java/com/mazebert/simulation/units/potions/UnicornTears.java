package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.Rarity;

public strictfp class UnicornTears extends Potion {

    private static final UnicornTearsAbility unicornTearsAbility = new UnicornTearsAbility();

    public UnicornTears() {
        super(unicornTearsAbility);
    }

    @Override
    public String getIcon() {
        return "9009_WisdomPotion";
    }

    @Override
    public String getName() {
        return "Unicorn Tears";
    }

    @Override
    public String getDescription() {
        return "Tears of the last unicorn.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Unique;
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
        return "2.0";
    }

    @Override
    public boolean isLight() {
        return true;
    }

    public void setLevels(int levels) {
        unicornTearsAbility.setLevels(levels);
    }
}
