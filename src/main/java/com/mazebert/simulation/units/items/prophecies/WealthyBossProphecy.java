package com.mazebert.simulation.units.items.prophecies;

import com.mazebert.simulation.Rarity;

public strictfp class WealthyBossProphecy extends Prophecy {

    public WealthyBossProphecy() {
        super(new WealthyBossProphecyAbility());
    }

    @Override
    public String getName() {
        return "A wealthy Boss";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Uncommon;
    }

    @Override
    public int getItemLevel() {
        return 1;
    }


}
