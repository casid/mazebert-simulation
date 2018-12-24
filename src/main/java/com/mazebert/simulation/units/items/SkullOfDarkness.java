package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.units.abilities.DarkItemAbility;

public strictfp class SkullOfDarkness extends Item {

    public SkullOfDarkness() {
        super(new SkullOfDarknessAbility(), new DarkItemAbility());
    }

    @Override
    public String getName() {
        return "Skull of Darkness";
    }

    @Override
    public String getDescription() {
        return "This skull thirsts for blood.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Legendary;
    }

    @Override
    public String getSinceVersion() {
        return "1.1";
    }

    @Override
    public String getIcon() {
        return "skull_512";
    }

    @Override
    public int getItemLevel() {
        return 66;
    }

    @Override
    public boolean isDropable() {
        return false;
    }

    @Override
    public boolean isDark() {
        return true;
    }
}
