package com.mazebert.simulation.usecases;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.commands.NextWaveCommand;

public class NextWave extends Usecase<NextWaveCommand> {
    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;

    @Override
    public void execute(NextWaveCommand command) {
        simulationListeners.onWaveStarted.dispatch();
    }
}
