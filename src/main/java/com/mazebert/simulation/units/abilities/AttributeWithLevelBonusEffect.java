package com.mazebert.simulation.units.abilities;

import com.mazebert.simulation.listeners.OnLevelChangedListener;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.towers.Tower;

public abstract strictfp class AttributeWithLevelBonusEffect extends Ability<Tower> implements OnLevelChangedListener {
    public final float bonus;
    public final float bonusPerLevel;
    private float currentBonus;

    public AttributeWithLevelBonusEffect(Tower origin, float bonus, float bonusPerLevel) {
        setOrigin(origin);
        this.bonus = bonus;
        this.bonusPerLevel = bonusPerLevel;
    }

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        getOriginTower().onLevelChanged.add(this);
        addBonus();
    }

    @Override
    protected void dispose(Tower unit) {
        removeBonus();
        getOriginTower().onLevelChanged.remove(this);
        super.dispose(unit);
    }

    private Tower getOriginTower() {
        return (Tower)getOrigin();
    }

    @Override
    public void onLevelChanged(Unit unit, int oldLevel, int newLevel) {
        removeBonus();
        addBonus();
    }

    private void addBonus() {
        if (currentBonus == 0) {
            currentBonus = bonus + (getOriginTower().getLevel() * bonusPerLevel);
            addToAttribute(currentBonus);
        }
    }

    private void removeBonus() {
        if (currentBonus > 0) {
            addToAttribute(-currentBonus);
            currentBonus = 0;
        }
    }

    protected abstract void addToAttribute(float amount);
}
