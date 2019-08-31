package com.mazebert.simulation.units.items;

import java.util.EnumSet;
import java.util.Set;

public strictfp class LightbladeAcademySetAbility extends ItemSetAbility {
    public LightbladeAcademySetAbility() {
        super(EnumSet.of(ItemType.LightbladeAcademyDrone, ItemType.LightbladeAcademySword));
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
        return "A strong force";
    }

    @Override
    public String getDescription() {
        return "+ 1 inventory slot (2 set items)";
    }
}
