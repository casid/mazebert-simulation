package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.Rarity;

public strictfp class Tears extends Potion {
    public Tears() {
        super(new TearsAbility());
    }

    @Override
    public String getIcon() {
        return "9006_TearsPotion";
    }

    @Override
    public String getName() {
        return "Tears of the Gods";
    }

    @Override
    public String getDescription() {
        return "There is no stronger force in the universe.\nUse it wisely.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Unique;
    }

    @Override
    public int getItemLevel() {
        return 80;
    }

    @Override
    public String getAuthor() {
        return "geX";
    }

    @Override
    public String getSinceVersion() {
        return "0.4";
    }
}
