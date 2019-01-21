package com.mazebert.simulation.nat.messages;

import org.jusecase.bitnet.message.BitMessage;

import java.util.UUID;

public class NatTraversalPoke extends BitMessage {
    public UUID gameId;
    public long playerId1;
    public long playerId2;
    public long localSecret;
    public long remoteSecret;
}
