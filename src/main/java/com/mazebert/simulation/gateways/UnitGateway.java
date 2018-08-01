package com.mazebert.simulation.gateways;

import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.creeps.Creep;

import java.util.ArrayList;
import java.util.List;

public strictfp class UnitGateway {
    private final List<Unit> units = new ArrayList<>();

    public void addUnit(Unit unit) {
        units.add(unit);
    }

    public List<Unit> getUnits() {
        return units;
    }

    public void removeUnit(Unit unit) {
        units.remove(unit);
    }

    @SuppressWarnings("unchecked")
    public <U extends Unit> U findUnitInRange(float x, float y, float range, Class<U> unitClass) {
        for (Unit unit : units) {
            if (unitClass.isAssignableFrom(unit.getClass())) {
                return (U)unit;
            }
        }
        return null;
    }
}
