package com.mazebert.simulation.units.items;

import com.mazebert.simulation.units.abilities.AuraAbility;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class BarrelAbility extends AuraAbility<Tower, Tower> {
    private static final float bonus = 0.1f;

    public BarrelAbility() {
        super(Tower.class, 1);
    }

    @Override
    protected void onAuraEntered(Tower unit) {
        unit.addAddedRelativeBaseDamage(bonus);
    }

    @Override
    protected void onAuraLeft(Tower unit) {
        unit.addAddedRelativeBaseDamage(-bonus);
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Tasty pints";
    }

    @Override
    public String getDescription() {
        return "The damage of towers in " + (int)getRange() + " range is increased by " + format.percent(bonus) + "%.";
    }
}
