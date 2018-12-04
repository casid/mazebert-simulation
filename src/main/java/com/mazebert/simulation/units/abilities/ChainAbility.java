package com.mazebert.simulation.units.abilities;

import com.mazebert.java8.Consumer;
import com.mazebert.java8.Predicate;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.listeners.OnDamageListener;
import com.mazebert.simulation.systems.DamageSystem;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class ChainAbility extends Ability<Tower> implements OnDamageListener, Consumer<Creep>, Predicate<Creep> {
    private final UnitGateway unitGateway = Sim.context().unitGateway;
    private final DamageSystem damageSystem = Sim.context().damageSystem;

    private int maxChains = -1;

    private Creep sourceCreep;
    private double chainDamage;
    private Creep[] chainedCreeps;
    private int chainedCreepCount;

    public ChainAbility(int maxChains) {
        setMaxChains(maxChains);
    }

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        unit.onDamage.add(this);
    }

    @Override
    protected void dispose(Tower unit) {
        unit.onDamage.remove(this);
        super.dispose(unit);
    }

    public void setMaxChains(int maxChains) {
        if (this.maxChains != maxChains) {
            this.maxChains = maxChains;
            chainedCreeps = new Creep[maxChains];
        }
    }

    @Override
    public void onDamage(Object origin, Creep target, double damage, int multicrits) {
        if (sourceCreep != null) {
            return;
        }

        beginChain(target, damage);
        unitGateway.forEach(Creep.class, this, this);
        endChain();
    }

    private void beginChain(Creep target, double damage) {
        chainDamage = damage;
        chainedCreepCount = 0;
        sourceCreep = target;
    }

    private void endChain() {
        getUnit().onChain.dispatch(sourceCreep, chainedCreeps, chainedCreepCount);

        sourceCreep = null;
        for (int i = 0; i < chainedCreepCount; ++i) {
            chainedCreeps[i] = null;
        }
        chainedCreepCount = 0;
    }

    @Override
    public void accept(Creep creep) {
        chainedCreeps[chainedCreepCount++] = creep;
        damageSystem.dealDamage(this, getUnit(), creep, chainDamage, 0);
    }

    @SuppressWarnings("RedundantIfStatement")
    @Override
    public boolean test(Creep creep) {
        if (creep == sourceCreep) {
            return false;
        }
        if (chainedCreepCount >= maxChains) {
            return false;
        }
        return true;
    }
}
