package com.mazebert.simulation.gateways;

import com.mazebert.java8.Consumer;
import com.mazebert.java8.Predicate;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.items.Item;
import com.mazebert.simulation.units.towers.Tower;
import com.mazebert.simulation.units.wizards.Wizard;
import com.mazebert.simulation.util.SafeIterationArray;

import java.util.concurrent.atomic.AtomicInteger;

public strictfp final class UnitGateway {
    private final SafeIterationArray<Unit> units = new SafeIterationArray<>();
    private final SafeIterationArray<Creep> creeps = new SafeIterationArray<>();
    private final SafeIterationArray<Tower> towers = new SafeIterationArray<>();

    private final CreepInRangePredicate creepInRangePredicate = new CreepInRangePredicate();
    private final CreepInRangeExcludedPredicate creepInRangeExcludedPredicate = new CreepInRangeExcludedPredicate();
    private final CreepInRangeConsumer creepInRangeConsumer = new CreepInRangeConsumer();

    public void addUnit(Unit unit) {
        units.add(unit);
        if (unit instanceof Creep) {
            creeps.add((Creep) unit);
        } else if (unit instanceof Tower) {
            towers.add((Tower) unit);
        }
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
        if (unit instanceof Creep) {
            creeps.remove((Creep) unit);
        } else if (unit instanceof Tower) {
            towers.remove((Tower) unit);
        }
        unit.onUnitRemoved.dispatch(unit);
        Sim.context().simulationListeners.onUnitRemoved.dispatch(unit);

        unit.dispose();
    }

    @SuppressWarnings("unchecked")
    public <U extends Unit> void removeAll(Class<U> unitClass, Predicate<U> predicate) {
        units.forEachIndexed((index, unit) -> {
            if (unitClass.isAssignableFrom(unit.getClass()) && predicate.test((U) unit)) {
                units.remove(index);
                if (unit instanceof Creep) {
                    creeps.remove((Creep) unit);
                } else if (unit instanceof Tower) {
                    towers.remove((Tower) unit);
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    public <U extends Unit> U findUnitInRange(float x, float y, float range, Class<U> unitClass) {
        return (U) units.find(unit -> unitClass.isAssignableFrom(unit.getClass()) && unit.isInRange(x, y, range));
    }

    public Creep findCreepInRange(float x, float y, float range) {
        creepInRangePredicate.x = x;
        creepInRangePredicate.y = y;
        creepInRangePredicate.range = range;
        return creeps.find(creepInRangePredicate);
    }

    public Creep findCreepInRange(float x, float y, float range, Creep[] excludedUnits) {
        creepInRangeExcludedPredicate.x = x;
        creepInRangeExcludedPredicate.y = y;
        creepInRangeExcludedPredicate.range = range;
        creepInRangeExcludedPredicate.excludedUnits = excludedUnits;
        return creeps.find(creepInRangeExcludedPredicate);
    }

    public Tower findRandomTowerInRange(float x, float y, float range) {
        return towers.findRandom(unit -> unit.isInRange(x, y, range), Sim.context().randomPlugin);
    }

    public Tower findTower(int playerId, int x, int y) {
        return towers.find(unit -> unit.getPlayerId() == playerId && unit.getX() == x && unit.getY() == y);
    }

    @SuppressWarnings("unchecked")
    public <U extends Unit> U findUnit(Class<U> unitClass, int playerId) {
        return (U) units.find(unit -> unitClass.isAssignableFrom(unit.getClass()) && unit.getPlayerId() == playerId);
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

    public void forEachCreep(Consumer<Creep> unitConsumer) {
        creeps.forEach(unitConsumer);
    }

    public void forEachTower(Consumer<Tower> unitConsumer) {
        towers.forEach(unitConsumer);
    }

    @SuppressWarnings("unchecked")
    public <U extends Tower> void forEachTower(Class<U> unitClass, Consumer<U> unitConsumer) {
        towers.forEach(unit -> {
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
        if (unitClass == Creep.class) {
            creepInRangeConsumer.x = x;
            creepInRangeConsumer.y = y;
            creepInRangeConsumer.range = range;
            creepInRangeConsumer.unitConsumer = unitConsumer;
            creeps.forEach(creepInRangeConsumer);
            creepInRangeConsumer.unitConsumer = null;
        } else if (unitClass == Tower.class) {
            towers.forEach(tower -> {
                if (tower.isInRange(x, y, range)) {
                    unitConsumer.accept((U) tower);
                }
            });
        } else {
            units.forEach(unit -> {
                if (unitClass.isAssignableFrom(unit.getClass()) && unit.isInRange(x, y, range)) {
                    unitConsumer.accept((U) unit);
                }
            });
        }
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

    @SuppressWarnings("unchecked")
    public  <T extends Unit> boolean hasUnits(Class<T> unitClass, Predicate<T> predicate) {
        return units.find(u -> {
            if (unitClass.isAssignableFrom(u.getClass())) {
                return predicate.test((T)u);
            }
            return false;
        }) != null;
    }

    public void destroyTower(Tower tower) {
        returnAllItemsToInventory(tower);
        removeUnit(tower);
    }

    public void returnAllItemsToInventory(Tower tower) {
        Wizard wizard = getWizard(tower.getPlayerId());
        returnAllItemsToInventory(wizard, tower);
    }

    public void returnAllItemsToInventory(Wizard wizard, Tower tower) {
        Item[] items = tower.removeAllItems();
        for (Item item : items) {
            if (item != null) {
                wizard.itemStash.add(item.getType());
            }
        }
    }

    private static final class CreepInRangePredicate implements Predicate<Creep> {

        public float x;
        public float y;
        public float range;

        @Override
        public boolean test(Creep creep) {
            return creep.isInRange(x, y, range);
        }
    }

    private static final class CreepInRangeExcludedPredicate implements Predicate<Creep> {

        public float x;
        public float y;
        public float range;
        public Creep[] excludedUnits;

        @Override
        public boolean test(Creep creep) {
            return creep.isInRange(x, y, range) && !contains(excludedUnits, creep);
        }

        private boolean contains(Creep[] excludedUnits, Creep unit) {
            for (Creep excludedUnit : excludedUnits) {
                if (excludedUnit == unit) {
                    return true;
                }
            }
            return false;
        }
    }

    private static final class CreepInRangeConsumer implements Consumer<Creep> {

        public float x;
        public float y;
        public float range;
        public Consumer unitConsumer;

        @Override
        public void accept(Creep creep) {
            if (creep.isInRange(x, y, range)) {
                //noinspection unchecked
                unitConsumer.accept(creep);
            }
        }
    }
}
