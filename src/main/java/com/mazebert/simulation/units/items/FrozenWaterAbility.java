package com.mazebert.simulation.units.items;

import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class FrozenWaterAbility extends Ability<Tower> {
    private static final float damageBonus = 0.3f;
    private static final float goldMalus = -0.2f;

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        unit.addAddedRelativeBaseDamage(damageBonus);
        unit.addGoldModifer(goldMalus);
    }


    @Override
    protected void dispose(Tower unit) {
        unit.addAddedRelativeBaseDamage(-damageBonus);
        unit.addGoldModifer(-goldMalus);
        super.dispose(unit);
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getLevelBonus() {
        return format.percentWithSignAndUnit(damageBonus) + " damage\n" + format.percentWithSignAndUnit(goldMalus) + " gold";
    }
}
