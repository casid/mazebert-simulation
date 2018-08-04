package com.mazebert.simulation.gateways;

import com.mazebert.simulation.messages.Turn;
import com.mazebert.simulation.replay.ReplayReader;
import com.mazebert.simulation.replay.data.ReplayHeader;
import com.mazebert.simulation.replay.data.ReplayTurn;

import java.util.List;

public class ReplayTurnGateway extends TurnGateway implements PlayerGateway {
    private final ReplayHeader replayHeader;
    private final ReplayReader replayReader;

    private ReplayTurn nextTurn;


    public ReplayTurnGateway(ReplayHeader replayHeader, ReplayReader replayReader) {
        super(replayHeader.playerCount);
        this.replayHeader = replayHeader;
        this.replayReader = replayReader;
    }

    @Override
    public List<Turn> waitForAllPlayerTurns(MessageGateway messageGateway) {
        return nextTurn.playerTurns;
    }

    @Override
    public int getPlayerId() {
        return replayHeader.playerId;
    }

    @Override
    public int getPlayerCount() {
        return replayHeader.playerCount;
    }

    public boolean hasNext() {
        try {
            nextTurn = replayReader.readTurn();
        } catch (Exception e) {
            nextTurn = null;
        }
        return nextTurn != null;
    }
}
