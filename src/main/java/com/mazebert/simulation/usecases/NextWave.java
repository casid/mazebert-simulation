package com.mazebert.simulation.usecases;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.commands.NextWaveCommand;

public strictfp class NextWave extends Usecase<NextWaveCommand> {
    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;

    @Override
    public void execute(NextWaveCommand command) {
        if (Sim.context().gameCountDown != null) {
            Sim.context().gameCountDown.stop();
        } else if (Sim.context().waveCountDown != null) {
            Sim.context().waveCountDown.stop();
        } else {
            simulationListeners.onWaveStarted.dispatch();
        }
    }
}
