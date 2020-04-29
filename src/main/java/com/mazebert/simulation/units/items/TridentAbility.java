package com.mazebert.simulation.units.items;

import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.abilities.AttackAbility;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class TridentAbility extends Ability<Tower> {

    private static final float attackSpeedMalus = -0.7f;
    private static final float damageMalus = -1.0f;

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        AttackAbility attackAbility = unit.getAbility(AttackAbility.class);
        if (attackAbility != null) {
            attackAbility.setTargets(attackAbility.getTargets() + 2);
        }
        unit.addAttackSpeed(attackSpeedMalus);
        unit.addAddedRelativeBaseDamage(damageMalus);
    }

    @Override
    protected void dispose(Tower unit) {
        AttackAbility attackAbility = unit.getAbility(AttackAbility.class);
        if (attackAbility != null) {
            attackAbility.setTargets(attackAbility.getTargets() - 2);
        }
        unit.addAttackSpeed(-attackSpeedMalus);
        unit.addAddedRelativeBaseDamage(-damageMalus);
        super.dispose(unit);
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Make Three out of One";
    }

    @Override
    public String getLevelBonus() {
        return "+2 targets.\n" + format.percentWithSignAndUnit(attackSpeedMalus) + " attack speed.\n" + format.percentWithSignAndUnit(damageMalus) + " damage.";
    }
}
