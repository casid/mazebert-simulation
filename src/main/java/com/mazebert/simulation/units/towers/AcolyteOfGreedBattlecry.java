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

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "GobGob! (Battlecry)";
    }

    @Override
    public String getDescription() {
        return "Treasure goblins appear, when you build the Acolyte.";
    }

    @Override
    public String getLevelBonus() {
        return "1 goblin at level 1-29\n2 goblins at level 30-59\n3 goblins at level 60-98\n5 goblins at level 99";
    }

    @Override
    public String getIconFile() {
        return "0041_purpledebuff_512";
    }
}
