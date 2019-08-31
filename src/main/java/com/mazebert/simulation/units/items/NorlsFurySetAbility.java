package com.mazebert.simulation.units.items;

import java.util.EnumSet;
import java.util.Set;

public strictfp class NorlsFurySetAbility extends ItemSetAbility {
    private static final float bonus = 0.1f;

    public NorlsFurySetAbility() {
        super(EnumSet.of(ItemType.NorlsFurySword, ItemType.NorlsFuryAmulet));
    }

    @Override
    protected void updateSetBonus(Set<ItemType> items, int oldAmount, int newAmount) {
        if (newAmount == 2) {
            getUnit().addCritChance(bonus);
        }

        if (oldAmount == 2) {
            getUnit().addCritChance(-bonus);
        }
    }

    @Override
    public String getTitle() {
        return "Norl's Fury";
    }

    @Override
    public String getDescription() {
        return format.percentWithSignAndUnit(bonus) + " crit chance (2 set items)";
    }
}
