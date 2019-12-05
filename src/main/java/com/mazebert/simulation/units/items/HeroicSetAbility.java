package com.mazebert.simulation.units.items;

import java.util.EnumSet;
import java.util.Set;

public strictfp class HeroicSetAbility extends ItemSetAbility {
    public static final float BONUS = 0.2f;

    public HeroicSetAbility() {
        super(EnumSet.of(ItemType.HeroicCape, ItemType.HeroicMask));
    }

    @Override
    protected void updateSetBonus(Set<ItemType> items, int oldAmount, int newAmount) {
        if (newAmount == 2) {
            getUnit().addAttackSpeed(BONUS);
        }

        if (oldAmount == 2) {
            getUnit().addAttackSpeed(-BONUS);
        }
    }

    @Override
    public String getTitle() {
        return "Heroic Rush";
    }

    @Override
    public String getDescription() {
        return format.percentWithSignAndUnit(BONUS) + " attack speed (2 set items)";
    }
}
