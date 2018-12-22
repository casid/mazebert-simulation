package com.mazebert.simulation.units.items;

import com.mazebert.simulation.units.abilities.AuraAbility;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class CauldronAbility extends AuraAbility<Tower, Tower> {
    private static final float bonus = 0.1f;

    public CauldronAbility() {
        super(Tower.class, 1);
    }

    @Override
    protected void onAuraEntered(Tower unit) {
        unit.addAttackSpeed(bonus);
    }

    @Override
    protected void onAuraLeft(Tower unit) {
        unit.addAttackSpeed(-bonus);
    }

    @Override
    public String getTitle() {
        return "Tasty potions";
    }

    @Override
    public String getDescription() {
        return "The attack speed of towers in " + getRange() + " range is increased by 10%.";
    }
}
