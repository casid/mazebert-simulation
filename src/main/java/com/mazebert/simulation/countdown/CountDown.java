package com.mazebert.simulation.countdown;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.listeners.OnUpdateListener;

public strictfp abstract class CountDown implements OnUpdateListener {

    protected final SimulationListeners simulationListeners = Sim.context().simulationListeners;

    private final float seconds;
    private float secondsPassed;
    private int lastUpdate;

    public CountDown(float seconds) {
        this.seconds = seconds;
    }

    @Override
    public void onUpdate(float dt) {
        secondsPassed += dt;
        if (secondsPassed >= seconds) {
            stop();
        } else if((int)secondsPassed > lastUpdate) {
            onCountDownUpdated((int)seconds - (int)secondsPassed);
            lastUpdate = (int)secondsPassed;
        }
    }

    public void start() {
        this.simulationListeners.onUpdate.add(this);
        onCountDownUpdated((int)seconds);
    }

    public void stop() {
        onCountDownReached(simulationListeners);
        simulationListeners.onUpdate.remove(this);
    }

    public void cancel() {
        simulationListeners.onUpdate.remove(this);
    }

    public int getRemainingSeconds() {
        return StrictMath.round(seconds - secondsPassed);
    }

    protected abstract void onCountDownReached(SimulationListeners simulationListeners);

    protected abstract void onCountDownUpdated(int remainingSeconds);
}
