package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.units.creeps.effects.DurationEffect;

public strictfp class WitheredSetEffect extends DurationEffect {
    private static final float damageModifier = 0.03f;

    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;

    private float current;

    @Override
    public void addStack() {
        super.addStack();

        if (simulationListeners.areNotificationsEnabled()) {
            simulationListeners.showNotification(getUnit(), "Amped!", 0x7b775b);
        }
    }

    @Override
    public void removeAllStacks() {
        super.removeAllStacks();

        if (simulationListeners.areNotificationsEnabled()) {
            simulationListeners.showNotification(getUnit(), "Lost!", 0x7b775b);
        }
    }

    @Override
    protected void updateStacks() {
        removeBonus();
        addBonus();
    }

    private void addBonus() {
        current = getStackCount() * damageModifier;
        getUnit().addDamageModifier(current);
    }

    private void removeBonus() {
        getUnit().addDamageModifier(-current);
    }
}
