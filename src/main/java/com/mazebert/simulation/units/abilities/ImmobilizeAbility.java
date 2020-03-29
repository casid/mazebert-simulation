package com.mazebert.simulation.units.abilities;

import com.mazebert.simulation.listeners.OnDamageListener;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.towers.Tower;

public strictfp abstract class ImmobilizeAbility extends Ability<Tower> implements OnDamageListener {
    private float chance;
    private float chancePerLevel;

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

        if (getUnit().isImmobilizeAbilityTriggered(getChance(target), target)) {
            immobilize(target);
        }
    }

    protected float getChance(Creep creep) {
        float chance = this.chance;
        if (chancePerLevel > 0) {
            chance += chancePerLevel * getUnit().getLevel();
        }
        return chance;
    }

    protected abstract void immobilize(Creep target);

    public float getChance() {
        return chance;
    }

    public void setChance(float chance) {
        this.chance = chance;
    }

    public void setChancePerLevel(float chancePerLevel) {
        this.chancePerLevel = chancePerLevel;
    }

    @Override
    public String getLevelBonus() {
        if (chancePerLevel > 0) {
            return format.percentWithSignAndUnit(chancePerLevel) + " chance per level.";
        }
        return super.getLevelBonus();
    }

    public float getCurrentChance() {
        return chance + getUnit().getLevel() * chancePerLevel;
    }
}
