package com.mazebert.simulation.units.items;

import com.mazebert.simulation.units.abilities.PoisonAbility;
import com.mazebert.simulation.units.creeps.Creep;

public strictfp class PoisonArrowAbility extends PoisonAbility {
    public static final float damage = 0.2f;
    public static final float damagePerLevel = 0.002f;
    public static final int duration = 4;

    public PoisonArrowAbility() {
        super(duration);
    }

    @Override
    protected double calculatePoisonDamage(Creep target, double damage, int multicrits) {
        return damage * (PoisonArrowAbility.damage + getUnit().getLevel() * damagePerLevel);
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Hydra Poison";
    }

    @Override
    public String getDescription() {
        return "Whenever the carrier damages a creep, that creep receives an additional " + format.percent(damage) + "% poison damage over " + duration + " seconds. This ability stacks.";
    }

    @Override
    public String getLevelBonus() {
        return format.percentWithSignAndUnit(damagePerLevel) + " poison damage per level";
    }
}
