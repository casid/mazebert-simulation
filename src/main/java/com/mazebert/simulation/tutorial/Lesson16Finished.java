package com.mazebert.simulation.tutorial;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.listeners.OnPauseListener;
import com.mazebert.simulation.units.wizards.Wizard;

public strictfp class Lesson16Finished extends Lesson07SecondWave implements OnPauseListener {

    @Override
    public void initialize(Wizard wizard) {
        super.initialize(wizard);
        Sim.context().simulationListeners.onPause.add(this);
    }

    @Override
    public void dispose(Wizard wizard) {
        Sim.context().simulationListeners.onPause.remove(this);
        super.dispose(wizard);
    }

    @Override
    public void onPause(int playerId, boolean pause) {
        if (!pause) {
            finish();
        }
    }
}
