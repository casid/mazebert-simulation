package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.Rarity;

public strictfp class CommonCrit extends Potion {
    public CommonCrit() {
        super(new CommonCritAbility());
    }

    @Override
    public String getIcon() {
        return "9000_CritPotion";
    }

    @Override
    public String getName() {
        return "Small Potion of Crit";
    }

    @Override
    public String getDescription() {
        return "Reaching the critical mass will leave a funny taste on your tounge!";
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
