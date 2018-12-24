package com.mazebert.simulation.units.items;

import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class ImpatienceWrathWatchAbility extends Ability<Tower> {
    private static final float attackSpeedBonus = 0.25f;
    private static final float damageMalus = -0.1f;

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        unit.addAttackSpeed(attackSpeedBonus);
        unit.addAddedRelativeBaseDamage(damageMalus);
    }

    @Override
    protected void dispose(Tower unit) {
        unit.addAttackSpeed(-attackSpeedBonus);
        unit.addAddedRelativeBaseDamage(-damageMalus);
        super.dispose(unit);
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getLevelBonus() {
        return format.percentWithSignAndUnit(attackSpeedBonus) + " attack speed\n" + format.percentWithSignAndUnit(damageMalus) + " damage";
    }
}
