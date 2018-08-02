package com.mazebert.simulation.units.abilities;

import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.towers.Tower;
import org.jusecase.inject.Component;

import javax.inject.Inject;

@Component
public strictfp class AttackAbility extends CooldownAbility<Tower> {

    @Inject
    private UnitGateway unitGateway;

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
        if (currentTarget != null && currentTarget.isInRange(tower, tower.getRange())) {
            return currentTarget;
        }

        return unitGateway.findUnitInRange(tower, tower.getRange(), Creep.class);
    }
}
