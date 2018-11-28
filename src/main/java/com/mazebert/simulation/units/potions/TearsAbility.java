package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class TearsAbility extends Ability<Tower> {
    private static final float damage = 0.25f;
    private static final float critDamage = 0.5f;
    private static final float critChance = 0.05f;
    private static final int multicrit = 1;

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);

        unit.addAddedRelativeBaseDamage(damage);
        unit.addCritChance(critChance);
        unit.addCritDamage(critDamage);
        unit.addMulticrit(multicrit);
    }

    @Override
    protected void dispose(Tower unit) {
        unit.addAddedRelativeBaseDamage(-damage);
        unit.addCritChance(-critChance);
        unit.addCritDamage(-critDamage);
        unit.addMulticrit(-multicrit);

        super.dispose(unit);
    }

    @Override
    public boolean isPermanent() {
        return true;
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Divine Intervention";
    }

    @Override
    public String getDescription() {
        return "+ " + formatPlugin.percent(damage) + "% damage\n" +
                "+ " + formatPlugin.percent(critDamage) + "% crit damage\n" +
                "+ " + formatPlugin.percent(critChance) + "% crit chance\n" +
                "+ " + multicrit + " multicrit";
    }
}
