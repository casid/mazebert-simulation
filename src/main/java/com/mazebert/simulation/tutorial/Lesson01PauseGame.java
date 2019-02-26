package com.mazebert.simulation.tutorial;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.listeners.OnPauseListener;
import com.mazebert.simulation.units.wizards.Wizard;

public strictfp class Lesson01PauseGame extends Lesson implements OnPauseListener {
    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;

    @Override
    public void initialize(Wizard wizard) {
        super.initialize(wizard);
        simulationListeners.onPause.add(this);
    }

    @Override
    public void dispose(Wizard wizard) {
        simulationListeners.onPause.remove(this);
        super.dispose(wizard);
    }

    @Override
    public void onPause(int playerId, boolean pause) {
        if (pause) {
            finish();
        }
    }
}
