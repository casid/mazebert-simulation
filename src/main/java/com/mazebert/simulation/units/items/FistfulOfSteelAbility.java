package com.mazebert.simulation.units.items;

import com.mazebert.simulation.units.abilities.AuraAbility;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class FistfulOfSteelAbility extends AuraAbility<Tower, Tower> {
    private static final float bonus = 0.05f;

    public FistfulOfSteelAbility() {
        super(Tower.class, 1);
    }

    @Override
    protected void onAuraEntered(Tower unit) {
        unit.addCritChance(bonus);
    }

    @Override
    protected void onAuraLeft(Tower unit) {
        unit.addCritChance(-bonus);
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Enrage the crowd";
    }

    @Override
    public String getDescription() {
        return "The crit chance of towers in " + getRange() + " range is increased by " + format.percent(bonus) + "%.";
    }
}
