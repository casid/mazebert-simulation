package com.mazebert.simulation.usecases;

import com.mazebert.simulation.Context;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.commands.NextWaveCommand;
import com.mazebert.simulation.countdown.EarlyCallCountDown;

public strictfp class NextWave extends Usecase<NextWaveCommand> {
    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;

    @Override
    public void execute(NextWaveCommand command) {
        Context context = Sim.context();
        if (context.earlyCallCountDown != null) {
            return;
        }

        if (context.gameCountDown != null) {
            context.gameCountDown.stop();
        } else if (context.waveCountDown != null) {
            context.waveCountDown.stop();
        } else {
            simulationListeners.onWaveStarted.dispatch();
        }

        context.earlyCallCountDown = new EarlyCallCountDown();
        context.earlyCallCountDown.start();
        simulationListeners.onEarlyCallImpossible.dispatch();
    }
}
