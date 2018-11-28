package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.Rarity;

public strictfp class UncommonCrit extends Potion {
    public UncommonCrit() {
        super(new UncommonCritAbility());
    }

    @Override
    public String getIcon() {
        return "9000_CritPotion";
    }

    @Override
    public String getName() {
        return "Potion of Crit";
    }

    @Override
    public String getDescription() {
        return "Reaching the critical mass will leave a funny taste on your tounge!";
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
