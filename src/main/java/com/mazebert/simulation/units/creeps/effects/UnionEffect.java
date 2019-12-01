package com.mazebert.simulation.units.creeps.effects;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.listeners.OnCreepHealthChangedListener;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class UnionEffect extends Ability<Creep> implements OnCreepHealthChangedListener {
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
    public void onHealthChanged(Tower tower, Creep creep, double oldHealth, double newHealth) {
        unitGateway.forEachCreep(c -> {
            if (c != creep && c.getWave() == creep.getWave()) {
                c.setHealth(tower, newHealth, false);
                if (c.isDead() && tower != null) {
                    tower.onKill.dispatch(c);
                }
            }
        });
    }
}
