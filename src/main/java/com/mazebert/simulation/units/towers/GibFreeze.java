package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.listeners.OnDamageListener;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.creeps.Creep;

public strictfp class GibFreeze extends Ability<Tower> implements OnDamageListener {
    public static final float slowMultiplier = 0.9f;
    public static final float slowDuration = 3.0f;
    public static final int stackCount = 3;
    public static final int stackEveryLevel = 14;


    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        unit.onDamage.add(this);
    }

    @Override
    protected void dispose(Tower unit) {
        unit.onDamage.remove(this);
        super.dispose(unit);
    }

    @Override
    public void onDamage(Object origin, Creep target, double damage, int multicrits) {
        GibFreezeEffect effect = target.addAbilityStack(getUnit(), GibFreezeEffect.class);
        effect.addStack(slowMultiplier, slowDuration, getMaxStackCount());
    }

    public int getMaxStackCount() {
        return stackCount + getUnit().getLevel() / stackEveryLevel;
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Frozen Bite";
    }

    @Override
    public String getDescription() {
        return "Whenever Gib damages a creep, that creep is slowed by " + format.percent(1.0f - slowMultiplier) + "% and its armor is increased by 1. These effects ends after " + format.seconds(slowDuration) + " and can stack " + stackCount + " times.";
    }

    @Override
    public String getIconFile() {
        return "frozen_water_512";
    }

    @Override
    public String getLevelBonus() {
        return "+ 1 stack every " + stackEveryLevel + " levels.";
    }
}
