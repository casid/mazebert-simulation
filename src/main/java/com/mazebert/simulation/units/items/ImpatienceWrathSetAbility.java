package com.mazebert.simulation.units.items;

import java.util.EnumSet;

public class ImpatienceWrathSetAbility extends ItemSetAbility {
    public ImpatienceWrathSetAbility() {
        super(EnumSet.of(ItemType.ImpatienceWrathWatch));
    }

    @Override
    protected void updateSetBonus(EnumSet<ItemType> items, int oldAmount, int newAmount) {
        // TODO
    }
}
