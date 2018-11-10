package com.mazebert.simulation.units.abilities;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.gateways.TurnGateway;
import com.mazebert.simulation.listeners.OnUpdateListener;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class DelayedDamageAbility extends DamageAbility implements OnUpdateListener {

    private final TurnGateway turnGateway = Sim.context().turnGateway;

    private final float delay;

    private float waitUntil;
    private Creep creepToAttack;
    private int lastAttackTurn;

    /**
     * @param delay in seconds (will be scaled according to tower attack speed)
     */
    public DelayedDamageAbility(float delay) {
        this.delay = delay;
    }

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        unit.onUpdate.add(this);
    }

    @Override
    protected void dispose(Tower unit) {
        unit.onUpdate.remove(this);
        super.dispose(unit);
    }

    @Override
    public void onAttack(Creep target) {
        float delay = this.delay / Math.max(1.0f, getUnit().getAttackSpeedModifier());
        if (delay > 0.1f) {
            lastAttackTurn = turnGateway.getCurrentTurnNumber();
            waitUntil = delay;
            creepToAttack = target;
        } else {
            super.onAttack(target);
        }
    }

    @Override
    public void onUpdate(float dt) {
        if (creepToAttack != null && waitUntil > 0 && lastAttackTurn < turnGateway.getCurrentTurnNumber()) {
            waitUntil -= dt;

            if (waitUntil <= 0) {
                super.onAttack(creepToAttack);
                creepToAttack = null;
            }
        }
    }
}
