package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;

public strictfp class Wolfskin extends Item {

    public Wolfskin() {
        super(new WolfskinAbility());
    }

    @Override
    public String getName() {
        return "Wolfskin Cloak";
    }

    @Override
    public String getDescription() {
        return "Even a sheep can look like a wolf in the right clothing.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Uncommon;
    }

    @Override
    public String getSinceVersion() {
        return "0.10";
    }

    @Override
    public String getIcon() {
        return "wolfskin_512";
    }

    @Override
    public int getItemLevel() {
        return 18;
    }

    @Override
    public String getAuthor() {
        return "skillchip";
    }
}
