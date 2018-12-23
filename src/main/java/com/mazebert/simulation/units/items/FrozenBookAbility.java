package com.mazebert.simulation.units.items;

import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class FrozenBookAbility extends Ability<Tower> {
    private static final float experienceBonus = 0.3f;
    private static final float damageMalus = -0.15f;

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        unit.addExperienceModifier(experienceBonus);
        unit.addAddedRelativeBaseDamage(damageMalus);
    }


    @Override
    protected void dispose(Tower unit) {
        unit.addExperienceModifier(-experienceBonus);
        unit.addAddedRelativeBaseDamage(-damageMalus);
        super.dispose(unit);
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getLevelBonus() {
        return format.percentWithSignAndUnit(experienceBonus) + " experience\n" + format.percentWithSignAndUnit(damageMalus) + " damage";
    }
}
