package com.mazebert.simulation;

import com.mazebert.java8.Consumer;
import com.mazebert.simulation.errors.DsyncException;
import com.mazebert.simulation.gateways.*;
import com.mazebert.simulation.replay.ReplayReader;
import com.mazebert.simulation.replay.data.ReplayHeader;

@SuppressWarnings("unused") // Used by ladder to validate simulation games
public strictfp class SimulationValidator {
    @SuppressWarnings("UnusedReturnValue")
    public Simulation validate(int version, ReplayReader replayReader, Consumer<Context> before, Consumer<Context> after) throws DsyncException {
        Context context = ContextProvider.createContext(false);
        context.version = version;

        context.replayWriterGateway = new NoReplayWriterGateway();
        context.messageGateway = new NoMessageGateway();

        ReplayHeader replayHeader = replayReader.readHeader();
        context.turnGateway = new GameTurnGateway(replayHeader.playerCount);
        context.playerGateway = new ReplayPlayerGateway(replayHeader);

        Sim.setContext(context);
        try {
            Simulation simulation = new Simulation();
            if (before != null) {
                before.accept(context);
            }

            simulation.load(replayReader);

            if (after != null) {
                after.accept(context);
            }
            return simulation;
        } finally {
            Sim.resetContext();
        }
    }
}
