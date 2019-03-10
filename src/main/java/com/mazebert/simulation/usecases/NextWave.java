package com.mazebert.simulation.usecases;

import com.mazebert.simulation.Context;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.commands.NextWaveCommand;
import com.mazebert.simulation.countdown.CountDown;
import com.mazebert.simulation.gateways.GameGateway;

public strictfp class NextWave extends Usecase<NextWaveCommand> {
    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;
    private final GameGateway gameGateway = Sim.context().gameGateway;

    @Override
    public void execute(NextWaveCommand command) {
        Context context = Sim.context();
        if (context.earlyCallCountDown != null) {
            return;
        }

        if (gameGateway.getGame().bonusRound) {
            if (context.bonusRoundCountDown != null) {
                skipCountDown(context, context.bonusRoundCountDown);
            }
            return;
        }

        if (context.gameCountDown != null) {
            skipCountDown(context, context.gameCountDown);
        } else if (context.waveCountDown != null) {
            skipCountDown(context, context.waveCountDown);
        } else {
            simulationListeners.onWaveStarted.dispatch();
        }
    }

    private void skipCountDown(Context context, CountDown countDown) {
        context.skippedSeconds += countDown.getRemainingSeconds();
        countDown.stop();
        context.simulationListeners.onSecondsSkipped.dispatch();
    }
}
