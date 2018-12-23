package com.mazebert.simulation.units.items;

import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class FrozenHeartAbility extends Ability<Tower> {
    private static final float critChanceMalus = -0.10f;
    private static final float critDamageBonus = 0.80f;
    private static final int multicritBonus = 1;

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        unit.addCritChance(critChanceMalus);
        unit.addCritDamage(critDamageBonus);
        unit.addMulticrit(multicritBonus);
    }

    @Override
    protected void dispose(Tower unit) {
        unit.addCritChance(-critChanceMalus);
        unit.addCritDamage(-critDamageBonus);
        unit.addMulticrit(-multicritBonus);
        super.dispose(unit);
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getLevelBonus() {
        return format.percentWithSignAndUnit(critChanceMalus) + " crit chance\n" +
                format.percentWithSignAndUnit(critDamageBonus) + " crit damage\n" +
                "+" + multicritBonus + " multicrit";
    }
}
