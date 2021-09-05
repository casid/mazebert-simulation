package com.mazebert.simulation.units.items.prophecies;

import com.mazebert.simulation.Rarity;

public strictfp class MassToBossProphecy extends Prophecy {

    public MassToBossProphecy() {
        super(new MassToBossProphecyAbility());
    }

    @Override
    public String getName() {
        return "Mass Enchantment";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Common;
    }

    @Override
    public int getItemLevel() {
        return 1;
    }


}
