package com.mazebert.simulation.units.abilities;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class AttackAbility extends CooldownAbility<Tower> {

    private final UnitGateway unitGateway = Sim.context().unitGateway;

    private Creep currentTarget;

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        currentTarget = null;
    }

    @Override
    public void dispose(Tower unit) {
        currentTarget = null;
        super.dispose(unit);
    }

    @Override
    boolean onCooldownReached() {
        currentTarget = findTarget();
        if (currentTarget != null) {
            getUnit().onAttack.dispatch(currentTarget);
        }
        return true;
    }

    private Creep findTarget() {
        Tower tower = getUnit();
        if (currentTarget != null && currentTarget.isInRange(tower, tower.getBaseRange())) {
            return currentTarget;
        }

        return unitGateway.findUnitInRange(tower, tower.getBaseRange(), Creep.class);
    }
}
