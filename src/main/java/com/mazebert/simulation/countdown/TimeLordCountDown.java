package com.mazebert.simulation.countdown;

import com.mazebert.simulation.Balancing;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;

public strictfp class TimeLordCountDown extends CountDown {

    public TimeLordCountDown() {
        super(Balancing.TIME_LORD_COUNTDOWN_SECONDS);
    }

    @Override
    protected void onCountDownReached(SimulationListeners simulationListeners) {
        simulationListeners.onTimeLordStarted.dispatch();
        Sim.context().timeLordCountDown = null;
    }

    @Override
    protected void onCountDownUpdated(int remainingSeconds) {
        simulationListeners.onTimeLordCountDown.dispatch(remainingSeconds);
    }
}
