package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.units.abilities.PoisonAbility;
import com.mazebert.simulation.units.creeps.Creep;

public strictfp class SolaraBurn extends PoisonAbility {
    private static final float burnDamage = 4.0f;
    private static final float burnDamagePerLevel = 0.08f;
    private static final float burnDuration = 8.0f;

    public SolaraBurn() {
        super(burnDuration);
    }

    public float getBurnDamageFactor() {
        return burnDamage + getUnit().getLevel() * burnDamagePerLevel;
    }

    @Override
    protected double calculatePoisonDamage(Creep target, double damage, int multicrits) {
        return getBurnDamageFactor() * damage;
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Bonfire";
    }

    @Override
    public String getDescription() {
        return "Damaged creeps receive " + format.percent(burnDamage) + "% fire damage over " + format.seconds(burnDuration) + ".\nThis ability stacks.";
    }

    @Override
    public String getIconFile() {
        return "0083_fire_attack_512";
    }
}
