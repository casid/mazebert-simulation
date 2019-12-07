package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;

public strictfp class HeroicCape extends Item {

    public HeroicCape() {
        super(new HeroicCapeAbility(), new HeroicSetAbility());
    }

    @Override
    public String getName() {
        return "Heroic Cape";
    }

    @Override
    public String getDescription() {
        return "It's sexy and it flows in the wind.";
    }

    @Override
    public String getIcon() {
        return "hero_cape";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Uncommon;
    }

    @Override
    public int getItemLevel() {
        return 15;
    }

    @Override
    public String getSinceVersion() {
        return "2.0";
    }

    @Override
    public boolean isLight() {
        return true;
    }
}
