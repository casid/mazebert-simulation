package com.mazebert.simulation.countdown;

import com.mazebert.simulation.Balancing;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;

public strictfp class EarlyCallCountDown extends CountDown {
    public EarlyCallCountDown() {
        super(Balancing.EARLY_CALL_COUNTDOWN_SECONDS);
    }

    @Override
    protected void onCountDownReached(SimulationListeners simulationListeners) {
        simulationListeners.onEarlyCallPossible.dispatch();
        Sim.context().earlyCallCountDown = null;
    }

    @Override
    protected void onCountDownUpdated(int remainingSeconds) {
        // nothing to do
    }
}
