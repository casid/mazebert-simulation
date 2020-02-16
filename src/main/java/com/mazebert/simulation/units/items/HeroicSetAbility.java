package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Sim;

import java.util.EnumSet;
import java.util.Set;

public strictfp class HeroicSetAbility extends ItemSetAbility {
    private final float bonus;

    public HeroicSetAbility() {
        super(EnumSet.of(ItemType.HeroicCape, ItemType.HeroicMask));
        if (Sim.context().version >= Sim.v20) {
            bonus = 0.4f;
        } else {
            bonus = 0.2f;
        }
    }

    @Override
    protected void updateSetBonus(Set<ItemType> items, int oldAmount, int newAmount) {
        if (newAmount == 2) {
            getUnit().addAttackSpeed(bonus);
        }

        if (oldAmount == 2) {
            getUnit().addAttackSpeed(-bonus);
        }
    }

    @Override
    public String getTitle() {
        return "Heroic Rush";
    }

    @Override
    public String getDescription() {
        return format.percentWithSignAndUnit(bonus) + " attack speed (2 set items)";
    }
}
