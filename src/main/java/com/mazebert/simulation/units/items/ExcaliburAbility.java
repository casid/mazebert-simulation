package com.mazebert.simulation.units.items;

import com.mazebert.simulation.units.abilities.DamageWithLevelBonusAbility;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class ExcaliburAbility extends DamageWithLevelBonusAbility {
    private static final float damageBonus = 0.5f;
    private static final float critDamageBonus = 0.25f;
    private static final int multicritBonus = 2;

    public ExcaliburAbility() {
        super(damageBonus, 0.01f);
    }

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        unit.addCritDamage(critDamageBonus);
        unit.addMulticrit(multicritBonus);
    }

    @Override
    protected void dispose(Tower unit) {
        unit.addCritDamage(-critDamageBonus);
        unit.addMulticrit(-multicritBonus);
        super.dispose(unit);
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Wrath of the King";
    }

    @Override
    public String getDescription() {
        return "Damage increased by " + format.percent(damageBonus) + "%\nCrit damage increased by " + format.percent(critDamageBonus) + "% \n+" + multicritBonus + " multicrit";
    }
}
