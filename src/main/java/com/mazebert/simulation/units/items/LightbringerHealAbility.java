package com.mazebert.simulation.units.items;

import com.mazebert.simulation.units.abilities.PoisonAbility;
import com.mazebert.simulation.units.creeps.Creep;

public strictfp class LightbringerHealAbility extends PoisonAbility {
    private static final float HEALING_AMOUNT = 0.5f;

    public LightbringerHealAbility() {
        super(5.0f);
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
}
