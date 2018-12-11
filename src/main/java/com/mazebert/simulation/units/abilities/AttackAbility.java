package com.mazebert.simulation.units.abilities;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class AttackAbility extends CooldownAbility<Tower> {

    private final UnitGateway unitGateway = Sim.context().unitGateway;

    private Creep[] currentTargets;
    private boolean canAttackSameTarget;

    public AttackAbility() {
        this(1);
    }

    public AttackAbility(int targets) {
        this(targets, false);
    }

    public AttackAbility(int targets, boolean canAttackSameTarget) {
        this.currentTargets = new Creep[targets];
        this.canAttackSameTarget = canAttackSameTarget;
    }

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        for (int i = 0; i < currentTargets.length; ++i) {
            currentTargets[i] = null;
        }
    }

    @Override
    public void dispose(Tower unit) {
        for (int i = 0; i < currentTargets.length; ++i) {
            currentTargets[i] = null;
        }
        super.dispose(unit);
    }

    @Override
    protected boolean onCooldownReached() {
        boolean anythingAttacked = false;
        for (int i = 0; i < currentTargets.length; ++i) {
            currentTargets[i] = findTarget(i);
            if (currentTargets[i] != null) {
                getUnit().onAttack.dispatch(currentTargets[i]);
                anythingAttacked = true;
            }
        }
        return anythingAttacked;
    }

    protected boolean hasTargetToAttack() {
        return findTarget(0) != null;
    }

    private Creep findTarget(int i) {
        Tower tower = getUnit();
        if (currentTargets[i] != null && currentTargets[i].isInRange(tower, tower.getBaseRange())) {
            return currentTargets[i];
        }

        Creep target = unitGateway.findUnitInRange(tower, tower.getBaseRange(), Creep.class, currentTargets);
        if (target == null && canAttackSameTarget) {
            target = unitGateway.findUnitInRange(tower, tower.getBaseRange(), Creep.class);
        }
        return target;
    }

    public void setTargets(int targets) {
        if (targets != currentTargets.length) {
            currentTargets = new Creep[targets];
        }
    }

    public int getTargets() {
        return currentTargets.length;
    }
}
