package com.mazebert.simulation.countdown;

import com.mazebert.simulation.Balancing;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.Wave;
import com.mazebert.simulation.units.creeps.Creep;

public strictfp class StallingPreventionCountDown extends CountDown {
    private final Wave wave;

    public StallingPreventionCountDown(Wave wave) {
        super(Balancing.STALLING_PREVENTION_COUNTDOWN_SECONDS);
        this.wave = wave;
    }

    @Override
    protected void onCountDownReached(SimulationListeners simulationListeners) {
        if (Sim.context().version >= Sim.v30) {
            Sim.context().stallingPreventionCountDown = null;
        }

        wave.forcedCompletion = true;

        Creep lastCreep = Sim.context().unitGateway.findUnit(Creep.class, creep -> creep.getWave() == wave);

        Sim.context().waveSpawner.completeWave(wave, lastCreep);

        if (Sim.context().version < Sim.v30) {
            Sim.context().stallingPreventionCountDown = null; // In newer versions, Do not override stallingPreventionCountDown  right after it was set in completeWave (if auto next wave is active)
        }

        if (simulationListeners.areNotificationsEnabled()) {
            Sim.context().unitGateway.forEachWizard(w -> simulationListeners.showNotification(w, "Forcing next wave after " + Sim.context().formatPlugin.seconds(Balancing.STALLING_PREVENTION_COUNTDOWN_SECONDS) + "!"));
        }
    }

    @Override
    protected void onCountDownUpdated(int remainingSeconds) {
        // Unused
    }
}
