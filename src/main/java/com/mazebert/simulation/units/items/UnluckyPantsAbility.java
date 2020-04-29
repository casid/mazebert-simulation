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
        return "Great Wingman, Though";
    }

    @Override
    public String getLevelBonus() {
        return format.percentWithSignAndUnit(-bonus) + " luck.\n" + format.percentWithSignAndUnit(bonus) + " luck to towers in " + (int)getRange() + " range.";
    }
}
