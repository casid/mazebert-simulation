package com.mazebert.simulation.countdown;

import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.listeners.OnUpdateListener;
import org.jusecase.inject.Component;

import javax.inject.Inject;

@Component
public strictfp abstract class CountDown implements OnUpdateListener {

    @Inject
    private SimulationListeners simulationListeners;

    private final float millis;
    private float millisPassed;

    public CountDown(float millis) {
        this.millis = millis;
    }

    @Override
    public void onUpdate(float dt) {
        millisPassed += dt;
        if (millisPassed >= millis ) {
            onCountDownReached(simulationListeners);
            stop();
        }
    }

    public void start() {
        this.simulationListeners.onUpdate.add(this);
    }

    public void stop() {
        simulationListeners.onUpdate.remove(this);
    }

    protected abstract void onCountDownReached(SimulationListeners simulationListeners);
}
