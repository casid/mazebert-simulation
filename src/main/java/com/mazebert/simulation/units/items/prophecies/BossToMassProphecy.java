package com.mazebert.simulation.units.items.prophecies;

import com.mazebert.simulation.Rarity;

public strictfp class BossToMassProphecy extends Prophecy {

    public BossToMassProphecy() {
        super(new BossToMassProphecyAbility());
    }

    @Override
    public String getName() {
        return "Boss Enchantment";
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
