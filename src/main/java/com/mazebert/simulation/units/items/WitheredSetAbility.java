package com.mazebert.simulation.units.items;

import java.util.EnumSet;

public strictfp class WitheredSetAbility extends ItemSetAbility {
    public WitheredSetAbility() {
        super(EnumSet.of(ItemType.WitheredCactus));
    }

    @Override
    protected void updateSetBonus(EnumSet<ItemType> items, int oldAmount, int newAmount) {

    }
}
