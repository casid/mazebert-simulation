package com.mazebert.simulation.usecases;

import com.mazebert.simulation.Balancing;
import com.mazebert.simulation.Context;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.commands.NextWaveCommand;
import com.mazebert.simulation.countdown.CountDown;
import com.mazebert.simulation.gateways.GameGateway;

public strictfp class NextWave implements Usecase<NextWaveCommand> {
    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;
    private final GameGateway gameGateway = Sim.context().gameGateway;
    private final int version = Sim.context().version;

    @Override
    public void execute(NextWaveCommand command) {
        Context context = Sim.context();
        if (context.earlyCallCountDown != null) {
            return;
        }

        if (gameGateway.getGame().timeLord) {
            if (context.timeLordCountDown != null) {
                gameGateway.getGame().bonusRoundSeconds += context.timeLordCountDown.getRemainingSeconds();
                skipCountDown(context, context.timeLordCountDown);
            }
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
            if (version > Sim.v11 && version < Sim.v16) {
                context.skippedSeconds += Balancing.WAVE_COUNTDOWN_SECONDS;
                context.simulationListeners.onSecondsSkipped.dispatch();
            }
            simulationListeners.onWaveStarted.dispatch(Balancing.WAVE_COUNTDOWN_SECONDS_INT);
        }

        if (Sim.context().version >= Sim.vDoL) {
            Sim.context().simulation.setPause(command.playerId, false);
        }
    }

    public static void skipCountDown(Context context, CountDown countDown) {
        context.skippedSeconds += countDown.getRemainingSeconds();
        countDown.stop();
        context.simulationListeners.onSecondsSkipped.dispatch();
    }
}
