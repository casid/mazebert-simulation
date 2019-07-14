package com.mazebert.simulation.usecases;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.commands.LoadingProgressCommand;

public strictfp class LoadingProgress extends Usecase<LoadingProgressCommand> {
    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;

    @Override
    public void execute(LoadingProgressCommand command) {
        simulationListeners.onLoadingProgress.dispatch(command.playerId, command.progress);
    }
}
