package com.mazebert.simulation;

import com.mazebert.java8.Consumer;
import com.mazebert.simulation.errors.DsyncException;
import com.mazebert.simulation.gateways.NoMessageGateway;
import com.mazebert.simulation.gateways.NoReplayWriterGateway;
import com.mazebert.simulation.gateways.ReplayPlayerGateway;
import com.mazebert.simulation.gateways.TurnGateway;
import com.mazebert.simulation.replay.ReplayReader;
import com.mazebert.simulation.replay.data.ReplayHeader;

@SuppressWarnings("unused") // Used by ladder to validate simulation games
public strictfp class SimulationValidator {
    public Simulation validate(ReplayReader replayReader, Consumer<Context> before, Consumer<Context> after) throws DsyncException {
        Context context = ContextProvider.createContext(false);

        context.replayWriterGateway = new NoReplayWriterGateway();
        context.messageGateway = new NoMessageGateway();

        ReplayHeader replayHeader = replayReader.readHeader();
        context.turnGateway = new TurnGateway(replayHeader.playerCount);
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
