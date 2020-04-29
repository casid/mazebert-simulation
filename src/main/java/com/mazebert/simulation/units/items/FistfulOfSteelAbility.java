package com.mazebert.simulation.units.items;

import com.mazebert.simulation.CardCategory;
import com.mazebert.simulation.units.abilities.AuraAbility;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class FistfulOfSteelAbility extends AuraAbility<Tower, Tower> {
    private static final float bonus = 0.05f;

    public FistfulOfSteelAbility() {
        super(CardCategory.Item, Tower.class, 1);
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
        return "Enrage the Crowd";
    }

    @Override
    public String getLevelBonus() {
        return format.percentWithSignAndUnit(bonus) + " crit chance for towers in " + (int)getRange() + " range.";
    }
}
