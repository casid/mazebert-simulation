package com.mazebert.simulation.countdown;

import com.mazebert.simulation.Balancing;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;

public strictfp class GameCountDown extends CountDown {

    public GameCountDown() {
        super(Balancing.GAME_COUNTDOWN_SECONDS);
    }

    @Override
    protected void onCountDownReached(SimulationListeners simulationListeners) {
        simulationListeners.onGameStarted.dispatch();
        Sim.context().gameCountDown = null;
    }

    @Override
    protected void onCountDownUpdated(int remainingSeconds) {
        simulationListeners.onGameCountDown.dispatch(remainingSeconds);
    }
}
