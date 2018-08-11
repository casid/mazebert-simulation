package com.mazebert.simulation.countdown;

import com.mazebert.simulation.Balancing;
import com.mazebert.simulation.SimulationListeners;
import org.jusecase.inject.Component;

@Component
public class GameCountDown extends CountDown {

    public GameCountDown() {
        super(Balancing.GAME_COUNTDOWN_SECONDS);
    }

    @Override
    protected void onCountDownReached(SimulationListeners simulationListeners) {
        simulationListeners.onGameStarted.dispatch();
    }
}
