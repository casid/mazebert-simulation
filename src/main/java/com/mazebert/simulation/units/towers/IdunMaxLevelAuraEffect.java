package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.Balancing;
import com.mazebert.simulation.listeners.OnWizardHealthChangedListener;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.abilities.Ability;

public strictfp class IdunMaxLevelAuraEffect extends Ability<Tower> implements OnWizardHealthChangedListener {

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);

        unit.getWizard().onHealthChanged.add(this);
        updateMaxLevel(unit.getWizard().health);
    }

    @Override
    protected void dispose(Tower unit) {
        unit.getWizard().onHealthChanged.remove(this);
        unit.addMaxLevel(Balancing.MAX_TOWER_LEVEL - unit.getMaxLevel());

        super.dispose(unit);
    }

    @Override
    public void onHealthChanged(Unit unit, double oldHealth, double newHealth) {
        updateMaxLevel(newHealth);
    }

    private void updateMaxLevel(double health) {
        int newMaxLevel = (int)StrictMath.round(50.0 * health);
        if (newMaxLevel <= 0) {
            newMaxLevel = 1;
        }

        int maxLevelAdjustment = newMaxLevel - getUnit().getMaxLevel();
        if (maxLevelAdjustment != 0) {
            getUnit().addMaxLevel(maxLevelAdjustment);
        }
    }
}
