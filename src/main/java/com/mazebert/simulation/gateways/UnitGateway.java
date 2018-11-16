package com.mazebert.simulation.gateways;

import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.wizards.Wizard;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public strictfp class UnitGateway {
    private final List<Unit> units = new ArrayList<>();
    private final List<Unit> unitsToRemove = new ArrayList<>();
    private boolean removeDirectly = true;

    public void addUnit(Unit unit) {
        units.add(unit);
    }

    public List<Unit> getUnits() {
        return units;
    }

    public void removeUnit(Unit unit) {
        if (removeDirectly) {
            units.remove(unit);
        } else {
            unitsToRemove.add(unit);
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
    public <U extends Unit> U findUnit(Class<U> unitClass) {
        for (Unit unit : units) {
            if (unitClass.isAssignableFrom(unit.getClass())) {
                return (U) unit;
            }
        }
        return null;
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

    public Wizard getWizard() {
        return findUnit(Wizard.class);
    }

    public boolean hasUnits(Class<? extends Unit> unitClass) {
        for (Unit unit : units) {
            if (unitClass.isAssignableFrom(unit.getClass()) && !unitsToRemove.contains(unit)) {
                return true;
            }
        }
        return false;
    }
}
