package com.mazebert.simulation.units.creeps.effects;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.listeners.OnHealthChangedListener;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.creeps.Creep;

public strictfp class UnionEffect extends Ability<Creep> implements OnHealthChangedListener {
    private final UnitGateway unitGateway = Sim.context().unitGateway;

    @Override
    protected void initialize(Creep unit) {
        super.initialize(unit);
        unit.onHealthChanged.add(this);
    }

    @Override
    protected void dispose(Creep unit) {
        unit.onHealthChanged.remove(this);
        super.dispose(unit);
    }

    @Override
    public void onHealthChanged(Unit u, double oldHealth, double newHealth) {
        Creep unit = (Creep)u;
        unitGateway.forEachCreep(creep -> {
            if (creep != unit && creep.getWave() == unit.getWave()) {
                creep.setHealth(newHealth, false);
            }
        });
    }
}
