package com.mazebert.simulation.units.abilities;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.listeners.OnDamageListener;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.creeps.effects.StunEffect;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class StunAbility extends Ability<Tower> implements OnDamageListener {
    private float chance;
    private float chancePerLevel;
    private float duration;

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
        if (!target.isPartOfGame()) {
            return;
        }
        if (!isOriginalDamage(origin)) {
            return;
        }
        if (target.isSteady()) {
            return;
        }

        float chance = this.chance;
        if (chancePerLevel > 0) {
            chance += chancePerLevel * getUnit().getLevel();
        }
        if (getUnit().isAbilityTriggered(chance)) {
            StunEffect stunEffect = target.addAbilityStack(StunEffect.class);
            stunEffect.setDuration(duration);

            if (Sim.context().simulationListeners.areNotificationsEnabled()) {
                Sim.context().simulationListeners.showNotification(target, getStunText(), getStunColor());
            }
        }
    }

    public int getStunColor() {
        return 0xefefef;
    }

    public String getStunText() {
        return "Stunned!";
    }

    public float getChance() {
        return chance;
    }

    public void setChance(float chance) {
        this.chance = chance;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    public void setChancePerLevel(float chancePerLevel) {
        this.chancePerLevel = chancePerLevel;
    }

    @Override
    public String getTitle() {
        return "Stun";
    }

    @Override
    public String getDescription() {
        return "Each attack has a " + format.percent(chance) + "% chance to stun the creep for " + duration + "s.";
    }

    @Override
    public String getLevelBonus() {
        if (chancePerLevel > 0) {
            return format.percent(chancePerLevel) + "% chance per level.";
        }
        return super.getLevelBonus();
    }
}
