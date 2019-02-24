package com.mazebert.simulation.tutorial;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.Wave;
import com.mazebert.simulation.listeners.OnWaveFinishedListener;
import com.mazebert.simulation.units.wizards.Wizard;

public strictfp class Lesson04KillCreeps extends Lesson implements OnWaveFinishedListener {
    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;

    @Override
    public void initialize(Wizard wizard) {
        super.initialize(wizard);
        simulationListeners.onWaveFinished.add(this);
    }

    @Override
    public void dispose(Wizard wizard) {
        simulationListeners.onWaveFinished.remove(this);
        super.dispose(wizard);
    }

    @Override
    public void onWaveFinished(Wave wave) {
        finish();
    }
}
