package com.mazebert.simulation.units.abilities;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.units.items.BowlingBallUnit;

public strictfp class FollowPathBowlingBallAbility extends FollowPathAbility<BowlingBallUnit> {
    private final UnitGateway unitGateway = Sim.context().unitGateway;

    @Override
    protected float getSpeed() {
        return 3.0f;
    }

    @Override
    protected void onTargetReached() {
        unitGateway.removeUnit(getUnit());
    }
}
