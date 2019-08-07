package com.mazebert.simulation.units.items;

import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class UselessMachineAbility extends Ability<Tower> {
    private static final float experienceMalus = -0.8f;
    private static final float attackSpeedBonus = 2.5f;

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        unit.addExperienceModifier(experienceMalus);
        unit.addAttackSpeed(attackSpeedBonus);
        unit.addDealNoDamage(1);
    }

    @Override
    protected void dispose(Tower unit) {
        unit.addExperienceModifier(-experienceMalus);
        unit.addAttackSpeed(-attackSpeedBonus);
        unit.addDealNoDamage(-1);
        super.dispose(unit);
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Do it yourself";
    }

    @Override
    public String getDescription() {
        return "Deal no damage\n" + format.percentWithSignAndUnit(experienceMalus) + " experience\n" + format.percentWithSignAndUnit(attackSpeedBonus) + " attack speed";
    }
}
