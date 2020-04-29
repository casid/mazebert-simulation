package com.mazebert.simulation.units.abilities;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.listeners.OnLevelChangedListener;
import com.mazebert.simulation.listeners.OnPotionEffectivenessChangedListener;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.towers.Tower;

public abstract strictfp class AttributeWithLevelBonusAbility extends StackableAbility<Tower> implements OnLevelChangedListener, OnPotionEffectivenessChangedListener {
    public final float bonus;
    public final float bonusPerLevel;

    private final int version = Sim.context().version;

    private float currentBonus;

    public AttributeWithLevelBonusAbility(float bonus, float bonusPerLevel) {
        this.bonus = bonus;
        this.bonusPerLevel = bonusPerLevel;
    }


    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        updateBonus();

        if (bonusPerLevel != 0) {
            unit.onLevelChanged.add(this);
        }

        if (version >= Sim.vDoL && isPermanent()) {
            unit.onPotionEffectivenessChanged.add(this);
        }
    }

    @Override
    protected void dispose(Tower unit) {
        if (version >= Sim.vDoL && isPermanent()) {
            unit.onPotionEffectivenessChanged.remove(this);
        }

        if (bonusPerLevel != 0) {
            unit.onLevelChanged.remove(this);
        }

        removeBonus();
        super.dispose(unit);
    }

    @Override
    protected void updateStacks() {
        updateBonus();
    }

    protected void updateBonus() {
        removeBonus();

        currentBonus = calculateBonus();
        addToAttribute(currentBonus);
    }

    protected float calculateBonus() {
        float result = getStackCount() * (bonus + getUnit().getLevel() * bonusPerLevel);

        if (version >= Sim.vDoL && isPermanent()) {
            result *= getUnit().getPotionEffectiveness();
        }

        return result;
    }

    protected void removeBonus() {
        addToAttribute(-currentBonus);
        currentBonus = 0;
    }

    protected abstract void addToAttribute(float amount);

    @Override
    public void onLevelChanged(Unit unit, int oldLevel, int newLevel) {
        updateBonus();
    }

    @Override
    public void onPotionEffectivenessChanged(Tower tower) {
        updateBonus();
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getLevelBonus() {
        if (bonusPerLevel <= 0.0f) {
            return format.percentWithSignAndUnit(bonus) + " " + getAttributeName() + ".";
        }
        return format.percentWithSignAndUnit(bonus) + " " + getAttributeName() + ".\n" + format.percentWithSignAndUnit(bonusPerLevel) + " " + getAttributeName() + " per level.";
    }

    protected abstract String getAttributeName();
}
