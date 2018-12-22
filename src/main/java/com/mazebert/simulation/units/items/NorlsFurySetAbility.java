package com.mazebert.simulation.units.items;

import java.util.EnumSet;

public class NorlsFurySetAbility extends ItemSetAbility {
    public NorlsFurySetAbility() {
        super(EnumSet.of(ItemType.NorlsFurySword, ItemType.NorlsFuryAmulet));
    }

    @Override
    protected void updateSetBonus(EnumSet<ItemType> items, int oldAmount, int newAmount) {
        if (newAmount == 2) {
            getUnit().addCritChance(0.1f);
        }

        if (oldAmount == 2) {
            getUnit().addCritChance(-0.1f);
        }
    }
}
