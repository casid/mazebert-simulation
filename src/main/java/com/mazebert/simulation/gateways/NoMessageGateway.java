package com.mazebert.simulation.gateways;

import org.jusecase.bitnet.message.BitMessage;

public class NoMessageGateway implements MessageGateway {
    @Override
    public void sendToOtherPlayers(BitMessage message) {
        // do nothing
    }

    @Override
    public void sendToPlayer(int playerId, BitMessage message) {
        // do nothing
    }
}
