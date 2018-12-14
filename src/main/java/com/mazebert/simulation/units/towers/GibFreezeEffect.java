package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.creeps.effects.SlowEffect;

public class GibFreezeEffect extends SlowEffect {

    @Override
    protected void dispose(Creep unit) {
        unit.addArmor(-getStackCount());
        super.dispose(unit);
    }

    @Override
    public void addStack(float slowMultiplier, float duration) {
        super.addStack(slowMultiplier, duration);
        getUnit().addArmor(1);
    }
}
