package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.units.creeps.effects.DurationEffect;

public strictfp class WitheredSetEffect extends DurationEffect {
    public static final int MAX_STACKS = 25;

    private static final float damageModifier = 0.03f;

    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;
    private final int version = Sim.context().version;

    private float current;

    @Override
    public void addStack() {
        if (version >= Sim.vDoL && getStackCount() >= MAX_STACKS) {
            return;
        }

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
