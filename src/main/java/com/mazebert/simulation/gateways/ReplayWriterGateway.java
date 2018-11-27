package com.mazebert.simulation.gateways;

import com.mazebert.simulation.messages.Turn;
import com.mazebert.simulation.replay.data.ReplayHeader;

import java.util.List;

public interface ReplayWriterGateway {
    boolean isWriteEnabled();

    void writeHeader(ReplayHeader header);

    void writeTurn(int currentTurnNumber, List<Turn> playerTurns);
}
