package com.mazebert.simulation;

import com.mazebert.simulation.gateways.NoMessageGateway;
import com.mazebert.simulation.gateways.NoReplayWriterGateway;
import com.mazebert.simulation.gateways.ReplayPlayerGateway;
import com.mazebert.simulation.gateways.TurnGateway;
import com.mazebert.simulation.replay.ReplayReader;
import com.mazebert.simulation.replay.data.ReplayHeader;

public strictfp class SimulationValidator {
    public static Simulation validate(ReplayReader replayReader) throws DsyncException {
        Context context = ContextProvider.createContext(false);

        context.replayWriterGateway = new NoReplayWriterGateway();
        context.messageGateway = new NoMessageGateway();

        ReplayHeader replayHeader = replayReader.readHeader();
        context.turnGateway = new TurnGateway(replayHeader.playerCount);
        context.playerGateway = new ReplayPlayerGateway(replayHeader);

        Sim.setContext(context);
        try {
            Simulation simulation = new Simulation();
            simulation.load(replayReader);
            return simulation;
        } finally {
            Sim.resetContext();
        }
    }
}
