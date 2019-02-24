package com.mazebert.simulation.tutorial;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.listeners.OnGameCountDownListener;
import com.mazebert.simulation.listeners.OnPauseListener;
import com.mazebert.simulation.units.wizards.Wizard;

public strictfp class Lesson01PauseGame extends Lesson implements OnPauseListener, OnGameCountDownListener {
    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;

    @Override
    public void initialize(Wizard wizard) {
        super.initialize(wizard);
        simulationListeners.onPause.add(this);
        simulationListeners.onGameCountDown.add(this);
    }

    @Override
    public void dispose(Wizard wizard) {
        simulationListeners.onPause.remove(this);
        simulationListeners.onGameCountDown.remove(this);
        super.dispose(wizard);
    }

    @Override
    public void onPause(int playerId, boolean pause) {
        if (pause) {
            finish();
        }
    }

    @Override
    public void onGameCountDown(int remainingSeconds) {
        if (remainingSeconds <= 5) {
            pauseGame();
        }
    }
}
