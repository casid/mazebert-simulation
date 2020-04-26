package com.mazebert.simulation.units.abilities;

import com.mazebert.java8.Consumer;
import com.mazebert.simulation.CardCategory;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.listeners.OnRangeChangedListener;
import com.mazebert.simulation.listeners.OnUnitAddedListener;
import com.mazebert.simulation.listeners.OnUnitRemovedListener;
import com.mazebert.simulation.listeners.OnUpdateListener;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.items.Item;
import com.mazebert.simulation.units.towers.Tower;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * @param <S> aura source
 * @param <T> aura target
 */
public abstract strictfp class AuraAbility<S extends Unit, T extends Unit> extends Ability<S> implements OnUpdateListener, Consumer<T>, OnUnitAddedListener, OnUnitRemovedListener, OnRangeChangedListener {
    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;
    private final UnitGateway unitGateway = Sim.context().unitGateway;
    private final int version = Sim.context().version;

    private final CardCategory origin;
    private final Class<T> targetClass;
    private final float initialRange;
    private float range;

    private T[] active;
    private int activeSize;

    public AuraAbility(CardCategory origin, Class<T> targetClass) {
        this(origin, targetClass, 0);
    }

    public AuraAbility(CardCategory origin, Class<T> targetClass, float range) {
        this.origin = origin;
        this.targetClass = targetClass;
        this.range = this.initialRange = range;
    }

    public void setRange(float range) {
        if (this.range != range) {
            this.range = range;
            updateAuraTargets();
        }
    }

    public float getRange() {
        return range;
    }

    @Override
    protected void initialize(S unit) {
        super.initialize(unit);

        if (initDirectly()) {
            initAura(unit);
            if (version >= Sim.v17) {
                simulationListeners.onUnitAdded.add(this);
                simulationListeners.onUnitRemoved.add(this);
            }
        } else {
            unit.onUnitAdded.add(this);
            unit.onUnitRemoved.add(this);
        }
    }

    private boolean initDirectly() {
        return origin != CardCategory.Tower || getOrigin() instanceof Item;
    }

    @SuppressWarnings("unchecked")
    private void initAura(S unit) {
        if (hasMovingTargets()) {
            unit.onUpdate.add(this);
        }

        if (isRangeDynamic()) {
            Tower tower = (Tower) unit;
            tower.onRangeChanged.add(this);
            setRange(tower.getRange());
        }

        this.active = (T[]) Array.newInstance(targetClass, 9);

        updateAuraTargets();
    }

    @Override
    protected void dispose(S unit) {
        disposeAura(unit);

        unit.onUnitAdded.remove(this);
        unit.onUnitRemoved.remove(this);

        if (version >= Sim.v17) {
            simulationListeners.onUnitAdded.remove(this);
            simulationListeners.onUnitRemoved.remove(this);
        }

        super.dispose(unit);
    }

    private void disposeAura(S unit) {
        if (version >= Sim.vCorona) {
            while (activeSize > 0) {
                onAuraLeft(active[--activeSize]);
            }
        } else {
            markAllCurrentTargetsAsUnvisited();
            removeAllUnvisitedTargets();
        }
        this.active = null;
        this.activeSize = 0;

        unit.onUpdate.remove(this);
        if (unit instanceof Tower) {
            ((Tower)unit).onRangeChanged.remove(this);
        }
    }

    protected boolean hasMovingTargets() {
        return targetClass.isAssignableFrom(Creep.class);
    }

    private boolean isRangeDynamic() {
        if (version >= Sim.v19) {
            return initialRange == 0 && getUnit() instanceof Tower;
        } else {
            return range == 0 && getUnit() instanceof Tower;
        }
    }

    @Override
    public void onUpdate(float dt) {
        updateAuraTargets();
    }

    @Override
    public void onUnitAdded(Unit unit) {
        if (unit == getUnit()) {
            unit.onUnitAdded.remove(this);
            unit.onUnitRemoved.remove(this);
            simulationListeners.onUnitAdded.add(this);
            simulationListeners.onUnitRemoved.add(this);
            initAura(getUnit());
        } else if (targetClass.isAssignableFrom(unit.getClass())) {
            updateAuraTargets();
        }
    }

    @Override
    public void onUnitRemoved(Unit unit) {
        if (unit == getUnit()) {
            simulationListeners.onUnitAdded.remove(this);
            simulationListeners.onUnitRemoved.remove(this);
            disposeAura(getUnit());
        } else if (targetClass.isAssignableFrom(unit.getClass())) {
            updateAuraTargets();
        }
    }

    @Override
    public void onRangeChanged(Tower tower) {
        setRange(tower.getRange());
    }

    public void updateAuraTargets() {
        if (active != null) {
            markAllCurrentTargetsAsUnvisited();

            unitGateway.forEachInRange(getUnit().getX(), getUnit().getY(), range, targetClass, this);
            removeAllUnvisitedTargets();
        }
    }

    @Override
    public void accept(T unit) {
        if (!isDisposed() && isQualifiedForAura(unit)) {
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

    protected void forEach(Consumer<T> consumer) {
        for (int i = 0; i < activeSize; ++i) {
            consumer.accept(active[i]);
        }
    }

    protected abstract void onAuraEntered(T unit);

    protected abstract void onAuraLeft(T unit);
}
