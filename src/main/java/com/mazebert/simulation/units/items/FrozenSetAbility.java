package com.mazebert.simulation.units.items;

import com.mazebert.simulation.units.towers.TowerType;

import java.util.EnumSet;
import java.util.Set;

public strictfp class FrozenSetAbility extends ItemSetAbility {
    public FrozenSetAbility() {
        super(EnumSet.of(ItemType.FrozenWater, ItemType.FrozenHeart, ItemType.FrozenCandle, ItemType.FrozenBook));
    }

    @Override
    protected void updateSetBonus(Set<ItemType> items, int oldAmount, int newAmount) {
        if (newAmount >= 2 && oldAmount < 2) {
            getUnit().addAbility(new FrozenSetSlowAbility());
        }
        if (newAmount < 2 && oldAmount >= 2) {
            getUnit().removeAbility(FrozenSetSlowAbility.class);
        }
        if (newAmount >= 4 && oldAmount < 4) {
            getUnit().addAbility(new FrozenSetSummonAbility());
        }
        if (newAmount < 4 && oldAmount >= 4) {
            getUnit().removeAbility(FrozenSetSummonAbility.class);
        }
    }

    @Override
    public String getTitle() {
        return "Frozen";
    }

    @Override
    public String getDescription() {
        return "Slow target by 10% (2 set items)\nKill a creep after wearing the set for at least 1 minute, to sacrifice the set for " + format.card(TowerType.Gib) + ". (4 set items)";
    }
}
