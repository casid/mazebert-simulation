package com.mazebert.simulation.gateways;

import com.mazebert.java8.Consumer;
import com.mazebert.java8.Predicate;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.items.Item;
import com.mazebert.simulation.units.items.ItemType;
import com.mazebert.simulation.units.towers.Tower;
import com.mazebert.simulation.units.wizards.Wizard;
import com.mazebert.simulation.util.SafeIterationArray;

import java.util.concurrent.atomic.AtomicInteger;

public strictfp final class UnitGateway {
    private final SafeIterationArray<Unit> units = new SafeIterationArray<>();

    public void addUnit(Unit unit) {
        units.add(unit);
        Sim.context().simulationListeners.onUnitAdded.dispatch(unit);
        unit.onUnitAdded.dispatch(unit);
    }

    public Unit getUnit(int index) {
        return units.get(index);
    }

    public int getAmount() {
        return units.size();
    }

    public <U extends Unit> int getAmount(Class<U> unitClass) {
        AtomicInteger amount = new AtomicInteger();
        units.forEach(unit -> {
            if (unitClass.isAssignableFrom(unit.getClass())) {
                amount.incrementAndGet();
            }
        });
        return amount.get();
    }

    public void removeUnit(Unit unit) {
        units.remove(unit);
        unit.onUnitRemoved.dispatch(unit);
        Sim.context().simulationListeners.onUnitRemoved.dispatch(unit);

        unit.dispose();
    }

    @SuppressWarnings("unchecked")
    public <U extends Unit> void removeAll(Class<U> unitClass, Predicate<U> predicate) {
        units.forEachIndexed((index, unit) -> {
            if (unitClass.isAssignableFrom(unit.getClass()) && predicate.test((U) unit)) {
                units.remove(index);
            }
        });
    }

    public <U extends Unit> U findUnitInRange(Unit unit, float range, Class<U> unitClass) {
        return findUnitInRange(unit.getX(), unit.getY(), range, unitClass);
    }

    @SuppressWarnings("unchecked")
    public <U extends Unit> U findUnitInRange(float x, float y, float range, Class<U> unitClass) {
        return (U) units.find(unit -> unitClass.isAssignableFrom(unit.getClass()) && unit.isInRange(x, y, range));
    }

    @SuppressWarnings("unchecked")
    public <U extends Unit> U findUnitInRange(Unit unit, float range, Class<U> unitClass, U[] excludedUnits) {
        return findUnitInRange(unit.getX(), unit.getY(), range, unitClass, excludedUnits);
    }

    @SuppressWarnings("unchecked")
    public <U extends Unit> U findUnitInRange(float x, float y, float range, Class<U> unitClass, U[] excludedUnits) {
        return (U) units.find(unit -> unitClass.isAssignableFrom(unit.getClass()) && unit.isInRange(x, y, range) && !contains(excludedUnits, unit));
    }

    private <U extends Unit> boolean contains(U[] excludedUnits, U unit) {
        for (U excludedUnit : excludedUnits) {
            if (excludedUnit == unit) {
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    public <U extends Unit> U findUnit(Class<U> unitClass, int playerId) {
        return (U) units.find(unit -> unitClass.isAssignableFrom(unit.getClass()) && unit.getPlayerId() == playerId);
    }

    @SuppressWarnings("unchecked")
    public <U extends Unit> U findUnit(Class<U> unitClass, int playerId, int x, int y) {
        return (U) units.find(unit -> unitClass.isAssignableFrom(unit.getClass()) && unit.getPlayerId() == playerId && unit.getX() == x && unit.getY() == y);
    }

    @SuppressWarnings("unchecked")
    public <U extends Unit> U findUnit(Class<U> unitClass, int playerId, int x, int y, Predicate<U> predicate) {
        return (U) units.find(unit -> unitClass.isAssignableFrom(unit.getClass()) && unit.getPlayerId() == playerId && unit.getX() == x && unit.getY() == y && predicate.test((U) unit));
    }

    public void forEach(Consumer<Unit> unitConsumer) {
        units.forEach(unitConsumer);
    }

    @SuppressWarnings("unchecked")
    public <U extends Unit> void forEach(Class<U> unitClass, Consumer<U> unitConsumer) {
        units.forEach(unit -> {
            if (unitClass.isAssignableFrom(unit.getClass())) {
                unitConsumer.accept((U) unit);
            }
        });
    }

    @SuppressWarnings("unchecked")
    public <U extends Unit> void forEach(Class<U> unitClass, Predicate<U> predicate, Consumer<U> unitConsumer) {
        units.forEach(unit -> {
            if (unitClass.isAssignableFrom(unit.getClass()) && predicate.test((U)unit)) {
                unitConsumer.accept((U) unit);
            }
        });
    }

    @SuppressWarnings("unchecked")
    public <U extends Unit> void forEachInRange(float x, float y, float range, Class<U> unitClass, Consumer<U> unitConsumer) {
        units.forEach(unit -> {
            if (unitClass.isAssignableFrom(unit.getClass()) && unit.isInRange(x, y, range)) {
                unitConsumer.accept((U) unit);
            }
        });
    }

    public Wizard getWizard(int playerId) {
        return findUnit(Wizard.class, playerId);
    }

    public boolean hasUnit(Unit unit) {
        return units.find(u -> u == unit) != null;
    }

    public boolean hasUnits(Class<? extends Unit> unitClass) {
        return units.find(u -> unitClass.isAssignableFrom(u.getClass())) != null;
    }

    public void destroyTower(Tower tower) {
        Item[] items = tower.removeAllItems();
        for (Item item : items) {
            if (item != null) {
                Wizard wizard = getWizard(tower.getPlayerId());
                wizard.itemStash.add(ItemType.forItem(item));
            }
        }
        removeUnit(tower);
    }
}
