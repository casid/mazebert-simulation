package com.mazebert.simulation.units.items;

import java.util.EnumSet;
import java.util.Set;

public strictfp class EldritchMarshSetAbility extends ItemSetAbility {
    public EldritchMarshSetAbility() {
        super(EnumSet.of(ItemType.EldritchMarshNecklace, ItemType.EldritchMarshRifle));
    }

    @Override
    protected void updateSetBonus(Set<ItemType> items, int oldAmount, int newAmount) {
        if (newAmount == 2) {
            getUnit().addInventorySize(1);
        }
        if (oldAmount == 2) {
            getUnit().addInventorySize(-1);
        }
    }

    @Override
    public String getTitle() {
        return "Wealthy Sea Captain";
    }

    @Override
    public String getDescription() {
        return "+1 inventory slot. (2 set items)";
    }
}
