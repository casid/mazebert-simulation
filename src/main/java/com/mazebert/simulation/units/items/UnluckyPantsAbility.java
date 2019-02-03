package com.mazebert.simulation.units.items;

import com.mazebert.simulation.CardCategory;
import com.mazebert.simulation.units.abilities.AuraAbility;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class UnluckyPantsAbility extends AuraAbility<Tower, Tower> {
    public static final float bonus = 0.2f;

    public UnluckyPantsAbility() {
        super(CardCategory.Item, Tower.class, 1);
    }

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        unit.addLuck(-bonus);
    }

    @Override
    protected void dispose(Tower unit) {
        unit.addLuck(bonus);
        super.dispose(unit);
    }

    @Override
    protected boolean isQualifiedForAura(Tower unit) {
        return unit != getUnit();
    }

    @Override
    protected void onAuraEntered(Tower unit) {
        unit.addLuck(bonus);
    }

    @Override
    protected void onAuraLeft(Tower unit) {
        unit.addLuck(-bonus);
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Everybody gets lucky but you.";
    }

    @Override
    public String getDescription() {
        String bonus = format.percent(UnluckyPantsAbility.bonus);
        return "The luck of towers in " + (int)getRange() + " range is increased by " + bonus + "%, while the luck of this tower is decreased by " + bonus + "%.";
    }
}
