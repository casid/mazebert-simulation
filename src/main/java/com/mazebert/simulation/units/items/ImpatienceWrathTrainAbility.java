package com.mazebert.simulation.units.items;

import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class ImpatienceWrathTrainAbility extends Ability<Tower> {
    private static final float attackSpeedBonus = 0.4f;
    private static final float critChanceMalus = -0.2f;

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        unit.addAttackSpeed(attackSpeedBonus);
        unit.addCritChance(critChanceMalus);
    }

    @Override
    protected void dispose(Tower unit) {
        unit.addAttackSpeed(-attackSpeedBonus);
        unit.addCritChance(-critChanceMalus);
        super.dispose(unit);
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getLevelBonus() {
        return format.percentWithSignAndUnit(attackSpeedBonus) + " attack speed.\n" + format.percentWithSignAndUnit(critChanceMalus) + " crit chance.";
    }
}
