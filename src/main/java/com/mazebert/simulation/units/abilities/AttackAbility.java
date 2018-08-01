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

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
    }

    @Override
    public void dispose(Tower unit) {
        super.dispose(unit);
    }

    @Override
    boolean onCooldownReached() {
        Creep creep = unitGateway.findUnitInRange(0, 0, 0, Creep.class);
        if (creep == null) {
            return false;
        }

        getUnit().onAttack.dispatch(creep);
        return true;
    }
}
