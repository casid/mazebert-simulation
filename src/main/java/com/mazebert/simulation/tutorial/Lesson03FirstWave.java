package com.mazebert.simulation.tutorial;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.listeners.OnGameStartedListener;
import com.mazebert.simulation.units.wizards.Wizard;

public strictfp class Lesson03FirstWave extends Lesson implements OnGameStartedListener {
    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;

    @Override
    public void initialize(Wizard wizard) {
        super.initialize(wizard);
        simulationListeners.onGameStarted.add(this);
    }

    @Override
    public void dispose(Wizard wizard) {
        simulationListeners.onGameStarted.remove(this);
        super.dispose(wizard);
    }

    @Override
    public void onGameStarted() {
        finish();
    }
}
