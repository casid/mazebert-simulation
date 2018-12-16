package com.mazebert.simulation.units.creeps.effects;

import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.creeps.CreepState;

public strictfp class StunEffect extends DurationEffect {
    @Override
    protected void initialize(Creep unit) {
        super.initialize(unit);
        if (!unit.isDead()) {
            unit.setState(CreepState.Hit);
        }
    }

    @Override
    protected void dispose(Creep unit) {
        if (!unit.isDead()) {
            unit.setState(CreepState.Running);
        }
        super.dispose(unit);
    }
}
