package com.mazebert.simulation.gateways;

import com.mazebert.simulation.replay.data.ReplayHeader;

public strictfp class ReplayPlayerGateway implements PlayerGateway {
    private final ReplayHeader replayHeader;

    public ReplayPlayerGateway(ReplayHeader replayHeader) {

        this.replayHeader = replayHeader;
    }

    @Override
    public int getLocalPlayerId() {
        return replayHeader.playerId;
    }

    @Override
    public int getPlayerCount() {
        return replayHeader.playerCount;
    }
}
