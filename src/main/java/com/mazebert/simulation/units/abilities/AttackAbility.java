package com.mazebert.simulation.units.abilities;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.towers.Tower;

import java.util.Arrays;

public strictfp class AttackAbility extends CooldownUnitAbility<Tower> {

    private final UnitGateway unitGateway = Sim.context().unitGateway;
    private final int version = Sim.context().version;

    private Creep[] currentTargets;
    private final boolean canAttackSameTarget;

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
        Arrays.fill(currentTargets, null);
    }

    @Override
    protected void dispose(Tower unit) {
        Arrays.fill(currentTargets, null);
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
        if (version >= Sim.v13) {
            for (int i = 0; i < currentTargets.length; ++i) {
                if (findTarget(i) != null) {
                    return true;
                }
            }
            return false;
        } else {
            return findTarget(0) != null;
        }
    }

    private Creep findTarget(int i) {
        Tower tower = getUnit();
        if (currentTargets[i] != null && currentTargets[i].isInRange(tower, tower.getRange())) {
            return currentTargets[i];
        }

        if (getTargets() == 1) {
            return unitGateway.findCreepInRange(tower.getX(), tower.getY(), tower.getRange());
        }

        Creep target = unitGateway.findCreepInRange(tower.getX(), tower.getY(), tower.getRange(), currentTargets);
        if (target == null && canAttackSameTarget) {
            target = unitGateway.findCreepInRange(tower.getX(), tower.getY(), tower.getRange());
        }
        return target;
    }

    public int getTargets() {
        return currentTargets.length;
    }

    public void setTargets(int targets) {
        if (targets != currentTargets.length) {
            currentTargets = new Creep[targets];
        }
    }
}
