package com.mazebert.simulation.units.items;

import com.mazebert.simulation.CardCategory;
import com.mazebert.simulation.units.abilities.AuraAbility;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class CauldronAbility extends AuraAbility<Tower, Tower> {
    private static final float bonus = 0.1f;

    public CauldronAbility() {
        super(CardCategory.Item, Tower.class, 1);
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
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Tasty Potions";
    }

    @Override
    public String getLevelBonus() {
        return format.percentWithSignAndUnit(bonus) + " attack speed for towers in " + (int)getRange() + " range.";
    }
}
