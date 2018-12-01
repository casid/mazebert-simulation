package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.abilities.Ability;

public strictfp class ScepterOfTimeAbility extends Ability<Unit> {
    @Override
    protected void initialize(Unit unit) {
        super.initialize(unit);
        Sim.context().simulation.adjustSpeed(2.0f);
    }

    @Override
    protected void dispose(Unit unit) {
        Sim.context().simulation.adjustSpeed(0.5f);
        super.dispose(unit);
    }

    @Override
    public String getTitle() {
        return "Ancient perception";
    }

    @Override
    public String getDescription() {
        return "Time passes 2x faster.";
    }
}
