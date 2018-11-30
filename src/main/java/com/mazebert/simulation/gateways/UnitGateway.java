package com.mazebert.simulation.gateways;

import com.mazebert.java8.Consumer;
import com.mazebert.java8.Predicate;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.items.Item;
import com.mazebert.simulation.units.items.ItemType;
import com.mazebert.simulation.units.towers.Tower;
import com.mazebert.simulation.units.wizards.Wizard;

import java.util.ArrayList;
import java.util.List;

public strictfp class UnitGateway {
    private final List<Unit> units = new ArrayList<>();
    private final List<Unit> unitsToRemove = new ArrayList<>();
    private boolean removeDirectly = true;

    public void addUnit(Unit unit) {
        units.add(unit);
        unit.onUnitAdded.dispatch(unit);
        if (Sim.context().simulationListeners != null) {
            Sim.context().simulationListeners.onUnitAdded.dispatch(unit);
        }
    }

    public Unit getUnit(int index) {
        return units.get(index);
    }

    public int getAmount() {
        return units.size();
    }

    public void removeUnit(Unit unit) {
        if (removeDirectly) {
            units.remove(unit);
        } else {
            unitsToRemove.add(unit);
        }

        unit.onUnitRemoved.dispatch(unit);

        unit.dispose();

        if (Sim.context().simulationListeners != null) {
            Sim.context().simulationListeners.onUnitRemoved.dispatch(unit);
        }
    }

    public <U extends Unit> U findUnitInRange(Unit unit, float range, Class<U> unitClass) {
        return findUnitInRange(unit.getX(), unit.getY(), range, unitClass);
    }

    @SuppressWarnings("unchecked")
    public <U extends Unit> U findUnitInRange(float x, float y, float range, Class<U> unitClass) {
        for (Unit unit : units) {
            if (unitClass.isAssignableFrom(unit.getClass()) && unit.isInRange(x, y, range)) {
                return (U) unit;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public <U extends Unit> U findUnitInRange(Unit unit, float range, Class<U> unitClass, U[] excludedUnits) {
        return findUnitInRange(unit.getX(), unit.getY(), range, unitClass, excludedUnits);
    }

    @SuppressWarnings("unchecked")
    public <U extends Unit> U findUnitInRange(float x, float y, float range, Class<U> unitClass, U[] excludedUnits) {
        for (Unit unit : units) {
            if (unitClass.isAssignableFrom(unit.getClass()) && unit.isInRange(x, y, range) && !contains(excludedUnits, unit)) {
                return (U) unit;
            }
        }
        return null;
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
        for (Unit unit : units) {
            if (unitClass.isAssignableFrom(unit.getClass()) && unit.getPlayerId() == playerId) {
                return (U) unit;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public <U extends Unit> U findUnit(Class<U> unitClass, int playerId, int x, int y) {
        for (Unit unit : units) {
            if (unitClass.isAssignableFrom(unit.getClass()) && unit.getPlayerId() == playerId && unit.getX() == x && unit.getY() == y) {
                return (U) unit;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public <U extends Unit> U findUnit(Class<U> unitClass, int playerId, int x, int y, Predicate<U> predicate) {
        for (Unit unit : units) {
            if (unitClass.isAssignableFrom(unit.getClass()) && unit.getPlayerId() == playerId && unit.getX() == x && unit.getY() == y && predicate.test((U)unit)) {
                return (U) unit;
            }
        }
        return null;
    }

    public void forEach(Consumer<Unit> unitConsumer) {
        for (Unit unit : units) {
            unitConsumer.accept(unit);
        }
    }

    @SuppressWarnings("unchecked")
    public <U extends Unit> void forEachInRange(float x, float y, float range, Class<U> unitClass, Consumer<U> unitConsumer) {
        for (Unit unit : units) {
            if (unitClass.isAssignableFrom(unit.getClass()) && unit.isInRange(x, y, range)) {
                unitConsumer.accept((U) unit);
            }
        }
    }

    public void startIteration() {
        removeDirectly = false;
    }

    public void endIteration() {
        removeDirectly = true;
        if (!unitsToRemove.isEmpty()) {
            units.removeAll(unitsToRemove);
            unitsToRemove.clear();
        }
    }

    public Wizard getWizard(int playerId) {
        return findUnit(Wizard.class, playerId);
    }

    public boolean hasUnit(Unit unit) {
        for (Unit u : units) {
            if (u == unit) {
                return true;
            }
        }
        return false;
    }

    public boolean hasUnits(Class<? extends Unit> unitClass) {
        for (Unit unit : units) {
            if (unitClass.isAssignableFrom(unit.getClass()) && !unitsToRemove.contains(unit)) {
                return true;
            }
        }
        return false;
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
