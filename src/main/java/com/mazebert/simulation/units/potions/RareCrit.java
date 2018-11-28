package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.Rarity;

public strictfp class RareCrit extends Potion {
    public RareCrit() {
        super(new RareCritAbility());
    }

    @Override
    public String getIcon() {
        return "9000_CritPotion";
    }

    @Override
    public String getName() {
        return "Great Potion of Crit";
    }

    @Override
    public String getDescription() {
        return "Reaching the critical mass will leave a funny taste on your tounge!";
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
