package com.mazebert.simulation.countdown;

import com.mazebert.simulation.Balancing;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;

public strictfp class BonusRoundCountDown extends CountDown {

    public BonusRoundCountDown() {
        super(Balancing.BONUS_COUNTDOWN_SECONDS);
    }

    @Override
    protected void onCountDownReached(SimulationListeners simulationListeners) {
        simulationListeners.onBonusRoundStarted.dispatch();
        Sim.context().bonusRoundCountDown = null;
    }

    @Override
    protected void onCountDownUpdated(int remainingSeconds) {
        simulationListeners.onBonusRoundCountDown.dispatch(remainingSeconds);
    }
}
