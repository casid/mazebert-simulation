package com.mazebert.simulation.gateways;

import org.jusecase.bitnet.message.BitMessage;

public interface MessageGateway {
    void sendToOtherPlayers(BitMessage message);
    void sendToPlayer(int playerId, BitMessage message);
}
