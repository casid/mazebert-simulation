package com.mazebert.simulation.countdown;

import com.mazebert.simulation.Balancing;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;

public strictfp class WaveCountDown extends CountDown {

    public WaveCountDown() {
        super(Balancing.WAVE_COUNTDOWN_SECONDS);
    }

    @Override
    protected void onCountDownReached(SimulationListeners simulationListeners) {
        simulationListeners.onWaveStarted.dispatch(0);
        Sim.context().waveCountDown = null;
    }

    @Override
    protected void onCountDownUpdated(int remainingSeconds) {
        simulationListeners.onWaveCountDown.dispatch(remainingSeconds);
    }
}
