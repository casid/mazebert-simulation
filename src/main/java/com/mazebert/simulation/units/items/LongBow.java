package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;

public strictfp class LongBow extends Item {

    public LongBow() {
        super(new LongBowAbility());
    }

    @Override
    public String getName() {
        return "Longbow";
    }

    @Override
    public String getDescription() {
        return "There's nothing better than a Welsh longbow to shoot some birds for lunch.";
    }

    @Override
    public String getIcon() {
        return "0100_Wooden_Bow_512";
    }

    @Override
    public String getAuthor() {
        return "Holger Herbricht";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Uncommon;
    }

    @Override
    public int getItemLevel() {
        return 18;
    }

    @Override
    public String getSinceVersion() {
        return "0.3";
    }
}
