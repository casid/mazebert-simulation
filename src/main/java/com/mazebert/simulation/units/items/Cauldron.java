package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;

public strictfp class Cauldron extends Item {

    public Cauldron() {
        super(new CauldronAbility());
    }

    @Override
    public String getName() {
        return "Herb Witch's Cauldron";
    }

    @Override
    public String getDescription() {
        return "This cauldron is the property of a very famous witch.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Rare;
    }

    @Override
    public String getSinceVersion() {
        return "0.4";
    }

    @Override
    public String getIcon() {
        return "cauldron_512";
    }

    @Override
    public int getItemLevel() {
        return 50;
    }
}
