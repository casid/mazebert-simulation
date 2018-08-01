package com.mazebert.simulation.gateways;

public strictfp interface PlayerGateway {
    int getPlayerId();
    int getPlayerCount();

    default boolean isHost() {
        return getPlayerId() == 1;
    }
}
