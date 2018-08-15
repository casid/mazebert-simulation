package com.mazebert.simulation.countdown;

import com.mazebert.simulation.Balancing;
import com.mazebert.simulation.SimulationListeners;
import org.jusecase.inject.Component;

@Component
public strictfp class WaveCountDown extends CountDown {

    public WaveCountDown() {
        super(Balancing.WAVE_COUNTDOWN_SECONDS);
    }

    @Override
    protected void onCountDownReached(SimulationListeners simulationListeners) {
        simulationListeners.onWaveStarted.dispatch();
    }
}
