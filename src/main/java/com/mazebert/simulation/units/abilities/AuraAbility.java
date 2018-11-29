package com.mazebert.simulation.units.abilities;

import com.mazebert.java8.Consumer;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.listeners.OnUpdateListener;
import com.mazebert.simulation.units.Unit;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * @param <S> aura source
 * @param <T> aura target
 */
public abstract strictfp class AuraAbility<S extends Unit, T extends Unit> extends Ability<S> implements OnUpdateListener, Consumer<T> {
    private final UnitGateway unitGateway = Sim.context().unitGateway;

    private final Class<T> targetClass;
    private float range;

    private T[] active;
    private int activeSize;

    public AuraAbility(Class<T> targetClass, float range) {
        this.targetClass = targetClass;
        this.range = range;
    }

    public void setRange(float range) {
        this.range = range;
    }

    public int getActiveSize() {
        return activeSize;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void initialize(S unit) {
        super.initialize(unit);
        unit.onUpdate.add(this);
        this.active = (T[]) Array.newInstance(targetClass, 9);
    }

    @Override
    protected void dispose(S unit) {
        markAllCurrentTargetsAsUnvisited();
        removeAllUnvisitedTargets();
        this.active = null;

        unit.onUpdate.remove(this);
        super.dispose(unit);
    }

    @Override
    public void onUpdate(float dt) {
        markAllCurrentTargetsAsUnvisited();
        unitGateway.forEachInRange(getUnit().getX(), getUnit().getY(), range, targetClass, this);
        removeAllUnvisitedTargets();
    }

    @Override
    public void accept(T unit) {
        if (isQualifiedForAura(unit)) {
            unit.visited = true;

            if (!containsTarget(unit)) {
                addTarget(unit);
            }
        }
    }

    protected boolean isQualifiedForAura(T unit) {
        return true;
    }

    private void markAllCurrentTargetsAsUnvisited() {
        for (int i = 0; i < activeSize; ++i) {
            active[i].visited = false;
        }
    }

    private void removeAllUnvisitedTargets() {
        for (int i = 0; i < activeSize; ++i) {
            if (!active[i].visited) {
                T inactive = active[i];

                --activeSize;
                if (activeSize > i) {
                    active[i] = active[activeSize];
                }
                active[activeSize] = null;
                --i;

                onAuraLeft(inactive);
            }
        }
    }

    private boolean containsTarget(T unit) {
        for (int i = 0; i < activeSize; ++i) {
            if (active[i] == unit) {
                return true;
            }
        }
        return false;
    }

    private void addTarget(T unit) {
        if (activeSize + 1 > active.length) {
            active = Arrays.copyOf(active, activeSize * 2);
        }
        active[activeSize++] = unit;

        onAuraEntered(unit);
    }

    protected abstract void onAuraEntered(T unit);

    protected abstract void onAuraLeft(T unit);
}
