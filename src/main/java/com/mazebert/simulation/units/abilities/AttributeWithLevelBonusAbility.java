package com.mazebert.simulation.units.abilities;

import com.mazebert.simulation.listeners.OnLevelChangedListener;
import com.mazebert.simulation.units.towers.Tower;

public abstract strictfp class AttributeWithLevelBonusAbility extends StackableAbility<Tower> implements OnLevelChangedListener {
    protected final float bonus;
    protected final float bonusPerLevel;

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
    }

    @Override
    protected void dispose(Tower unit) {
        if (bonusPerLevel != 0) {
            unit.onLevelChanged.remove(this);
        }
        removeBonus();
        super.dispose(unit);
    }

    @Override
    public void addStack() {
        super.addStack();
        updateBonus();
    }

    private void updateBonus() {
        removeBonus();

        currentBonus = getStackCount() * (bonus + getUnit().getLevel() * bonusPerLevel);
        addToAttribute(currentBonus);
    }

    private void removeBonus() {
        addToAttribute(-currentBonus);
        currentBonus = 0;
    }

    protected abstract void addToAttribute(float amount);

    @Override
    public void onLevelChanged(Tower tower, int oldLevel, int newLevel) {
        updateBonus();
    }
}
