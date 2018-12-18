package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.creeps.effects.SlowEffect;

public strictfp class GibFreezeEffect extends SlowEffect {
    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;

    @Override
    public boolean addStack(float slowMultiplier, float duration, int maxStacks) {
        if (super.addStack(slowMultiplier, duration, maxStacks)) {
            getUnit().addArmor(1);

            if (simulationListeners.areNotificationsEnabled()) {
                simulationListeners.showNotification(getUnit(), "Frozen!", 0x3355aa);
            }
            return true;
        }
        return false;
    }

    @Override
    protected void dispose(Creep unit) {
        if (simulationListeners.areNotificationsEnabled()) {
            simulationListeners.showNotification(getUnit(), "Melted!", 0x3355aa);
        }

        unit.addArmor(-getStackCount());
        super.dispose(unit);
    }

}