package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.WaveSpawner;
import com.mazebert.simulation.listeners.OnUnitAddedListener;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.abilities.Ability;

public strictfp class AcolyteOfGreedBattlecry extends Ability<Tower> implements OnUnitAddedListener {

    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;
    private final WaveSpawner waveSpawner = Sim.context().waveSpawner;

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        unit.onUnitAdded.add(this);
    }

    @Override
    protected void dispose(Tower unit) {
        unit.onUnitAdded.remove(this);
        super.dispose(unit);
    }

    @Override
    public void onUnitAdded(Unit unit) {
        if (simulationListeners.areNotificationsEnabled()) {
            simulationListeners.showNotification(unit, "GobGob!", 0xffff00);
        }
        waveSpawner.spawnTreasureGoblins(unit.getWizard(), calculateAmountOfSpawnedGoblins());
    }

    private int calculateAmountOfSpawnedGoblins() {
        int level = getUnit().getLevel();
        if (level < 30) {
            return 1;
        } else if (level < 60) {
            return 2;
        } else if (level < 99) {
            return 3;
        }
        return 5;
    }
}
