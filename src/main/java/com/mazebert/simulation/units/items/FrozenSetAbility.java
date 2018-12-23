package com.mazebert.simulation.units.items;

import java.util.EnumSet;

public strictfp class FrozenSetAbility extends ItemSetAbility {
    public FrozenSetAbility() {
        super(EnumSet.of(ItemType.FrozenWater, ItemType.FrozenHeart));
    }

    @Override
    protected void updateSetBonus(EnumSet<ItemType> items, int oldAmount, int newAmount) {
        if (newAmount == 2 && oldAmount < 2) {
            getUnit().addAbility(new FrozenSetSlowAbility());
        }
        if (newAmount < 2 && oldAmount >= 2) {
            getUnit().removeAbility(FrozenSetSlowAbility.class);
        }
    }

    @Override
    public String getTitle() {
        return "Frozen";
    }

    @Override
    public String getDescription() {
        return "Slow target by 10% (2 set items)\nKill a creep after wearing the set for at least 1 minute, to sacrifice the set for a frozen daemon tower card. (4 set items)";
    }
}
