package com.mazebert.simulation.units.towers;

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

import java.util.ArrayList;
import java.util.List;

public strictfp class BearHunterPlaceTrap extends CooldownAbility<Tower> implements OnUnitAddedListener, OnUnitRemovedListener, OnRangeChangedListener {
    private final UnitGateway unitGateway = Sim.context().unitGateway;
    private final GameGateway gameGateway = Sim.context().gameGateway;
    private final RandomPlugin randomPlugin = Sim.context().randomPlugin;
    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;

    private int[] possibleGridIndices;
    private int possibleGridIndicesSize;
    private List<BearHunterTrap> traps = new ArrayList<>();

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
        // TODO dispose all traps
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
                trap = new BearHunterTrap();
                trap.setX(x);
                trap.setY(y);
                traps.add(trap);
                unitGateway.addUnit(trap);
            }
            trap.addStack();

            if (simulationListeners.areNotificationsEnabled()) {
                simulationListeners.showNotification(getUnit(), "Trap planted!", 0x666666);
            }
        }

        return true;
    }

    private BearHunterTrap getTrap(int x, int y) {
        for (BearHunterTrap trap : traps) {
            if (trap.getX() == x && trap.getY() == y) {
                return trap;
            }
        }
        return null;
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
}
