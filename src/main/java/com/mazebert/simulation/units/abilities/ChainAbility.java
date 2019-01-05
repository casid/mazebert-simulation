package com.mazebert.simulation.units.abilities;

import com.mazebert.java8.Consumer;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.listeners.OnDamageListener;
import com.mazebert.simulation.projectiles.ChainViewType;
import com.mazebert.simulation.systems.DamageSystem;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class ChainAbility extends Ability<Tower> implements OnDamageListener, Consumer<Creep> {
    private final UnitGateway unitGateway = Sim.context().unitGateway;
    private final DamageSystem damageSystem = Sim.context().damageSystem;

    private final ChainViewType viewType;

    private int maxChains = -1;

    private Creep sourceCreep;
    private double chainDamage;
    private Creep[] chainedCreeps;
    private int chainedCreepCount;

    public ChainAbility(ChainViewType viewType, int maxChains) {
        this.viewType = viewType;
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

    public int getMaxChains() {
        return maxChains;
    }

    @Override
    public void onDamage(Object origin, Creep target, double damage, int multicrits) {
        if (sourceCreep != null) {
            return;
        }

        if (!isOriginalDamage(origin)) {
            return;
        }

        chain(target, damage);
    }

    protected void chain(Creep target, double damage) {
        beginChain(target, damage);
        unitGateway.forEachCreep(this);
        endChain();
    }

    private void beginChain(Creep target, double damage) {
        chainDamage = damage;
        chainedCreepCount = 0;
        sourceCreep = target;
    }

    private void endChain() {
        getUnit().onChain.dispatch(viewType, sourceCreep, chainedCreeps, chainedCreepCount);

        for (int i = 0; i < chainedCreepCount; ++i) {
            damageSystem.dealDamage(this, getUnit(), chainedCreeps[i], chainDamage, 0, false);
            chainedCreeps[i] = null;
        }

        sourceCreep = null;
        chainedCreepCount = 0;
    }

    @Override
    public void accept(Creep creep) {
        if (creep == sourceCreep) {
            return;
        }
        if (creep.isDead()) {
            return;
        }
        if (chainedCreepCount >= maxChains) {
            return;
        }

        chainedCreeps[chainedCreepCount++] = creep;
    }
}
