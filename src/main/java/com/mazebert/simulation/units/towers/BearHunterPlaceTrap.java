package com.mazebert.simulation.units.towers;

import com.mazebert.java8.Predicate;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.gateways.GameGateway;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.listeners.OnRangeChangedListener;
import com.mazebert.simulation.listeners.OnUnitAddedListener;
import com.mazebert.simulation.listeners.OnUnitRemovedListener;
import com.mazebert.simulation.maps.MapGrid;
import com.mazebert.simulation.maps.Tile;
import com.mazebert.simulation.plugins.random.RandomPlugin;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.abilities.CooldownAbility;
import com.mazebert.simulation.units.traps.BearHunterTrap;

public strictfp class BearHunterPlaceTrap extends CooldownAbility<Tower> implements OnUnitAddedListener, OnUnitRemovedListener, OnRangeChangedListener, Predicate<BearHunterTrap> {
    private final UnitGateway unitGateway = Sim.context().unitGateway;
    private final GameGateway gameGateway = Sim.context().gameGateway;
    private final RandomPlugin randomPlugin = Sim.context().randomPlugin;
    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;

    private int[] possibleGridIndices;
    private int possibleGridIndicesSize;

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        unit.onUnitAdded.add(this);
        unit.onUnitRemoved.add(this);
        unit.onRangeChanged.add(this);
    }

    @Override
    public void dispose(Tower unit) {
        unit.onRangeChanged.remove(this);
        unit.onUnitRemoved.remove(this);
        unit.onUnitAdded.remove(this);
        super.dispose(unit);
    }

    @Override
    public void onUnitAdded(Unit unit) {
        calculatePossibleGridIndices();
    }

    @Override
    public void onUnitRemoved(Unit unit) {
        unitGateway.removeAll(BearHunterTrap.class, this);
    }

    @Override
    public void onRangeChanged(Tower tower) {
        calculatePossibleGridIndices();
    }

    @Override
    protected boolean onCooldownReached() {
        if (possibleGridIndicesSize > 0) {
            int indexRoll = randomPlugin.getInt(0, possibleGridIndicesSize - 1);
            int gridIndex = possibleGridIndices[indexRoll];

            MapGrid grid = gameGateway.getMap().getGrid();

            int x = grid.getX(gridIndex);
            int y = grid.getY(gridIndex);


            BearHunterTrap trap = getTrap(x, y);
            if (trap == null) {
                trap = new BearHunterTrap(getUnit());
                trap.setWizard(getUnit().getWizard());
                trap.setX(x);
                trap.setY(y);
                unitGateway.addUnit(trap);
            }
            trap.addStack();

            if (simulationListeners.areNotificationsEnabled()) {
                simulationListeners.showNotification(getUnit(), "Trap planted!", 0x666666);
            }
        }

        return true;
    }

    @Override
    public boolean test(BearHunterTrap bearHunterTrap) {
        return bearHunterTrap.getOrigin() == getUnit();
    }

    private BearHunterTrap getTrap(int x, int y) {
        return unitGateway.findUnit(BearHunterTrap.class, getUnit().getPlayerId(), x, y, this);
    }

    private void calculatePossibleGridIndices() {
        int possibleGridIndicesCapacity = getUnit().getTilesInRange() - 1; // can't place trap on trapper

        if (possibleGridIndices == null || possibleGridIndices.length < possibleGridIndicesCapacity) {
            possibleGridIndices = new int[possibleGridIndicesCapacity];
            possibleGridIndicesSize = 0;
        }

        MapGrid grid = gameGateway.getMap().getGrid();
        int range = (int) getUnit().getRange();

        for (int y = -range; y <= range; ++y) {
            for (int x = -range; x <= range; ++x) {
                int px = (int) getUnit().getX() + x;
                int py = (int) getUnit().getY() + y;
                Tile tile = grid.getTile(px, py);
                if (tile != null && tile.type.walkable) {
                    possibleGridIndices[possibleGridIndicesSize++] = grid.getIndex(px, py);
                }
            }
        }
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Bear Trap";
    }

    @Override
    public String getDescription() {
        return "Every " + format.cooldown(getUnit().getBaseCooldown()) + "s the bear hunter places a trap somewhere in his range. Traps can stack.";
    }

    @Override
    public String getIconFile() {
        return "0028_hide_512";
    }

    @Override
    public String getLevelBonus() {
        return "+ 0.5% attackspeed per level\n- 70% damage to air";
    }
}
