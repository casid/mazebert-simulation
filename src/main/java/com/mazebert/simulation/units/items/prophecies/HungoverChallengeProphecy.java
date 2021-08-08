package com.mazebert.simulation.units.items.prophecies;

import com.mazebert.simulation.Rarity;

public strictfp class HungoverChallengeProphecy extends Prophecy {

    public HungoverChallengeProphecy() {
        super(new HungoverChallengeProphecyAbility());
    }

    @Override
    public String getName() {
        return "A hungover Challenge";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Rare;
    }

    @Override
    public int getItemLevel() {
        return 7;
    }


}
