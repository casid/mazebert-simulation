package com.mazebert.simulation.gateways;

import com.mazebert.simulation.replay.data.ReplayHeader;

public class ReplayPlayerGateway implements PlayerGateway {
    private final ReplayHeader replayHeader;

    public ReplayPlayerGateway(ReplayHeader replayHeader) {

        this.replayHeader = replayHeader;
    }

    @Override
    public int getPlayerId() {
        return replayHeader.playerId;
    }

    @Override
    public int getPlayerCount() {
        return replayHeader.playerCount;
    }
}
