package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;

public strictfp class WitheredCactus extends Item {

    public WitheredCactus() {
        super(new WitheredCactusAbility(), new WitheredSetAbility());
    }

    @Override
    public String getName() {
        return "Dried Cactus";
    }

    @Override
    public String getDescription() {
        return "The happy days of this cactus are gone.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Uncommon;
    }

    @Override
    public String getSinceVersion() {
        return "0.8";
    }

    @Override
    public String getIcon() {
        return "withered_cactus_512";
    }

    @Override
    public int getItemLevel() {
        return 49;
    }

    @Override
    public String getAuthor() {
        return "Infinity";
    }
}
