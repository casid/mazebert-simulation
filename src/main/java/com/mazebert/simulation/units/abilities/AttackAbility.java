package com.mazebert.simulation.units.abilities;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.listeners.OnAttackOrderedListener;
import com.mazebert.simulation.listeners.OnUnitAddedListener;
import com.mazebert.simulation.listeners.OnUnitRemovedListener;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.towers.Tower;
import com.mazebert.simulation.units.wizards.Wizard;

import java.util.Arrays;

public strictfp class AttackAbility extends CooldownAbility<Tower> implements OnUnitAddedListener, OnUnitRemovedListener, OnAttackOrderedListener {

    private final UnitGateway unitGateway = Sim.context().unitGateway;
    private final int version = Sim.context().version;

    private Creep orderedTarget;
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
        unit.onUnitAdded.add(this);
        unit.onUnitRemoved.add(this);
        Arrays.fill(currentTargets, null);
        orderedTarget = null;
    }

    @Override
    public void dispose(Tower unit) {
        orderedTarget = null;
        Arrays.fill(currentTargets, null);
        unit.onUnitRemoved.remove(this);
        unit.onUnitAdded.remove(this);
        super.dispose(unit);
    }

    @Override
    public void onUnitAdded(Unit unit) {
        if (unit.getWizard() != null) {
            unit.getWizard().onAttackOrdered.add(this);
        }
    }

    @Override
    public void onUnitRemoved(Unit unit) {
        if (unit.getWizard() != null) {
            unit.getWizard().onAttackOrdered.remove(this);
        }
    }

    @Override
    public void onAttackOrdered(Wizard wizard, Creep creep) {
        orderedTarget = creep;
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
        if (i == 0 && orderedTarget != null) {
            if (orderedTarget.isPartOfGame()) {
                currentTargets[i] = orderedTarget;
            } else {
                orderedTarget = null;
            }
        }

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

    public void setTargets(int targets) {
        if (targets != currentTargets.length) {
            currentTargets = new Creep[targets];
        }
    }

    public int getTargets() {
        return currentTargets.length;
    }
}
