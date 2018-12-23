package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;

public strictfp class WitheredBandages extends Item {

    public WitheredBandages() {
        super(new WitheredBandagesAbility(), new WitheredSetAbility());
    }

    @Override
    public String getName() {
        return "Mummy Bandages";
    }

    @Override
    public String getDescription() {
        return "Haunted spirits are restless.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Unique;
    }

    @Override
    public String getSinceVersion() {
        return "0.8";
    }

    @Override
    public String getIcon() {
        return "withered_bandages_512";
    }

    @Override
    public int getItemLevel() {
        return 99;
    }

    @Override
    public String getAuthor() {
        return "Infinity";
    }
}
