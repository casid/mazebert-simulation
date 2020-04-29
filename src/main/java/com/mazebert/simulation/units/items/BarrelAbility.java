package com.mazebert.simulation.units.items;

import com.mazebert.simulation.CardCategory;
import com.mazebert.simulation.units.abilities.AuraAbility;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class BarrelAbility extends AuraAbility<Tower, Tower> {
    private static final float bonus = 0.1f;

    public BarrelAbility() {
        super(CardCategory.Item, Tower.class, 1);
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
        return "Tasty Pints";
    }

    @Override
    public String getLevelBonus() {
        return format.percentWithSignAndUnit(bonus) + " damage for towers in " + (int)getRange() + " range.";
    }
}
