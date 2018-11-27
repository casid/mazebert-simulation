package com.mazebert.simulation;

import com.mazebert.simulation.gateways.NoMessageGateway;
import com.mazebert.simulation.gateways.NoReplayWriterGateway;
import com.mazebert.simulation.gateways.ReplayPlayerGateway;
import com.mazebert.simulation.gateways.ReplayTurnGateway;
import com.mazebert.simulation.replay.ReplayReader;
import com.mazebert.simulation.replay.data.ReplayHeader;

public strictfp class SimulationValidator {
    public static Simulation validate(ReplayReader replayReader) throws DsyncException {
        Context context = ContextProvider.createContext(false);

        context.replayWriterGateway = new NoReplayWriterGateway();
        context.messageGateway = new NoMessageGateway();

        ReplayHeader replayHeader = replayReader.readHeader();
        ReplayTurnGateway turnGateway = new ReplayTurnGateway(replayHeader, replayReader);
        context.turnGateway = turnGateway;
        context.playerGateway = new ReplayPlayerGateway(replayHeader);

        Sim.setContext(context);
        Simulation simulation = new Simulation();
        simulation.start();

        while (turnGateway.hasNext()) {
            simulation.process();
        }

        return simulation;
    }
}
