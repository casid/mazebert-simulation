package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;

public strictfp class WitheredToadstool extends Item {

    public WitheredToadstool() {
        super(new WitheredToadstoolAbility(), new WitheredSetAbility());
    }

    @Override
    public String getName() {
        return "Rotten Toadstool";
    }

    @Override
    public String getDescription() {
        return "This toadstool tastes funny.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Common;
    }

    @Override
    public String getSinceVersion() {
        return "0.8";
    }

    @Override
    public String getIcon() {
        return "withered_toadstool_512";
    }

    @Override
    public int getItemLevel() {
        return 53;
    }

    @Override
    public String getAuthor() {
        return "Infinity";
    }
}
