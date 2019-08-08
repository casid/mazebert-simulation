package com.mazebert.simulation.units.creeps.effects;

import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.creeps.Creep;

public strictfp class GhostEffect extends Ability<Creep> {

    public static final float CHANCE_TO_MISS = 0.5f;

    @Override
    protected void initialize(Creep unit) {
        super.initialize(unit);
        unit.addChanceToMiss(CHANCE_TO_MISS);
    }

    @Override
    protected void dispose(Creep unit) {
        unit.addChanceToMiss(-CHANCE_TO_MISS);
        super.dispose(unit);
    }

}
