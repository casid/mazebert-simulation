package com.mazebert.simulation.units.items;

import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class FrozenCandleAbility extends Ability<Tower> {
    private static final float critChanceBonus = 0.25f;
    private static final float critDamageBonus = 0.20f;
    private static final int multicritMalus = -1;

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        unit.addCritChance(critChanceBonus);
        unit.addCritDamage(critDamageBonus);
        unit.addMulticrit(multicritMalus);
    }

    @Override
    protected void dispose(Tower unit) {
        unit.addCritChance(-critChanceBonus);
        unit.addCritDamage(-critDamageBonus);
        unit.addMulticrit(-multicritMalus);
        super.dispose(unit);
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getLevelBonus() {
        return format.percentWithSignAndUnit(critChanceBonus) + " crit chance\n" +
                format.percentWithSignAndUnit(critDamageBonus) + " crit damage\n" +
                + multicritMalus + " multicrit";
    }
}
