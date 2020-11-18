package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.listeners.OnDamageListener;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.creeps.Creep;

public strictfp class SpiderWeb extends Ability<Tower> implements OnDamageListener {
    private static final float chance = 0.18f;
    private static final float chanceAgainstBoss = 0.09f;
    private static final float slow = 0.2f;
    private static final float slowDuration = 8.0f;
    private static final float slowPerLevel = 0.004f;
    private static final int maxStackCount = 3;

    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;

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
        if (getUnit().isAbilityTriggered(calculateChanceToSlowTarget(target))) {
            float slowMultiplier = 1.0f - (slow + slowPerLevel * getUnit().getLevel());
            SpiderWebEffect effect = target.addAbilityStack(getUnit(), SpiderWebEffect.class);
            if (effect != null && effect.addStack(slowMultiplier, slowDuration, maxStackCount) && simulationListeners.areNotificationsEnabled()) {
                simulationListeners.showNotification(target, "Webbed!", 0xaaaaaa);
            }
        }
    }

    private float calculateChanceToSlowTarget(Creep target) {
        if (target.isBoss()) {
            return chanceAgainstBoss;
        } else {
            return chance;
        }
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Webspinner";
    }

    @Override
    public String getDescription() {
        return "Each attack has an " + format.percent(chance) + "% chance (" + format.percent(chanceAgainstBoss) + "% against bosses) to slow the target by " + format.percent(slow) + "% for " + format.seconds(slowDuration) + ". Can stack up to " + maxStackCount + " times.";
    }

    @Override
    public String getLevelBonus() {
        return format.percentWithSignAndUnit(slowPerLevel) + " slow per level.";
    }

    @Override
    public String getIconFile() {
        return "spiderweb_512";
    }
}
