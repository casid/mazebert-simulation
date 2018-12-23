package com.mazebert.simulation.units.creeps.effects;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.listeners.OnDeadListener;
import com.mazebert.simulation.plugins.random.RandomPlugin;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.creeps.Creep;

public strictfp class SecondChanceEffect extends Ability<Creep> implements OnDeadListener {
    private final RandomPlugin randomPlugin = Sim.context().randomPlugin;
    private int resurrections;

    @Override
    protected void initialize(Creep unit) {
        super.initialize(unit);
        unit.onDead.add(this);
    }

    @Override
    protected void dispose(Creep unit) {
        unit.onDead.remove(this);
        super.dispose(unit);
    }

    @Override
    public void onDead(Creep creep) {
        if (resurrections < 4 && randomPlugin.getFloatAbs() < 0.5f) {
            creep.resurrect(0.5 * creep.getMaxHealth());
            ++resurrections;
        }
    }
}
