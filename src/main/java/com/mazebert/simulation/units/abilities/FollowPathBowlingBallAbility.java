package com.mazebert.simulation.units.abilities;

import com.mazebert.java8.Consumer;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.WaveType;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.items.BowlingBallUnit;

public strictfp class FollowPathBowlingBallAbility extends FollowPathAbility<BowlingBallUnit> implements Consumer<Creep> {
    private final UnitGateway unitGateway = Sim.context().unitGateway;

    @Override
    protected float getSpeed() {
        return 3.0f;
    }

    @Override
    protected void onTargetReached() {
        unitGateway.removeUnit(getUnit());
    }

    @Override
    public void onUpdate(float dt) {
        super.onUpdate(dt);
        if (!isDisposed()) {
            unitGateway.forEachInRange(getUnit().getX(), getUnit().getY(), 1, Creep.class, this);
        }
    }

    @Override
    public void accept(Creep creep) {
        if (isDisposed()) {
            return;
        }

        WaveType waveType = creep.getWave().type;
        if (waveType.isBoss()) {
            onTargetReached();
        } else if (waveType != WaveType.Air) {
            getUnit().getTower().kill(creep);
        }
    }
}
