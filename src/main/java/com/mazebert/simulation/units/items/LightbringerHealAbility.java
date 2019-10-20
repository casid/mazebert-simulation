package com.mazebert.simulation.units.items;

import com.mazebert.simulation.units.abilities.PoisonAbility;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class LightbringerHealAbility extends PoisonAbility {
    private static final float HEALING_AMOUNT = 0.5f;
    private static final int MULTICRIT = 3;

    public LightbringerHealAbility() {
        super(5.0f);
    }

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        unit.addMulticrit(MULTICRIT);
    }

    @Override
    protected void dispose(Tower unit) {
        unit.addMulticrit(-MULTICRIT);
        super.dispose(unit);
    }

    @Override
    protected double calculatePoisonDamage(Creep target, double damage, int multicrits) {
        return -HEALING_AMOUNT * damage;
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Healing";
    }

    @Override
    public String getDescription() {
        return format.percentWithSignAndUnit(HEALING_AMOUNT) + " of Lucifer's damage is healed back over " + format.seconds(getDuration()) + ".";
    }

    @Override
    public String getLevelBonus() {
        return "+" + MULTICRIT + " multicrit";
    }
}
