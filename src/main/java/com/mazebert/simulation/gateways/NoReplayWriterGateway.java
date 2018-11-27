package com.mazebert.simulation.gateways;

import com.mazebert.simulation.messages.Turn;
import com.mazebert.simulation.replay.ReplayReader;
import com.mazebert.simulation.replay.data.ReplayHeader;

import java.util.List;

public strictfp class NoReplayWriterGateway implements ReplayWriterGateway {
    @Override
    public boolean isWriteEnabled() {
        return false;
    }

    @Override
    public void writeHeader(ReplayHeader header) {
        throw new UnsupportedOperationException("This gateway is not designed to write anything");
    }

    @Override
    public void writeTurn(int currentTurnNumber, List<Turn> playerTurns) {
        throw new UnsupportedOperationException("This gateway is not designed to write anything");
    }

    @Override
    public void writePreviousReplay(ReplayReader reader) {
        throw new UnsupportedOperationException("This gateway is not designed to write anything");
    }

    @Override
    public void close() {
        throw new UnsupportedOperationException("This gateway is not designed to write anything");
    }
}
