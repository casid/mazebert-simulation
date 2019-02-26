package com.mazebert.simulation.tutorial;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.listeners.OnUnitAddedListener;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.towers.Hitman;
import com.mazebert.simulation.units.wizards.Wizard;

public strictfp class Lesson12ReplaceTower extends Lesson implements OnUnitAddedListener {
    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;

    @Override
    public void initialize(Wizard wizard) {
        super.initialize(wizard);
        simulationListeners.onUnitAdded.add(this);
    }

    @Override
    public void dispose(Wizard wizard) {
        simulationListeners.onUnitAdded.remove(this);
        super.dispose(wizard);
    }

    @Override
    public void onUnitAdded(Unit unit) {
        if (unit instanceof Hitman) {
            finish();
        }
    }
}
