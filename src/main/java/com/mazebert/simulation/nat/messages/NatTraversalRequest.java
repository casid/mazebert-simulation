package com.mazebert.simulation.nat.messages;

import org.jusecase.bitnet.message.BitMessage;

import java.net.InetSocketAddress;
import java.util.UUID;

public class NatTraversalRequest extends BitMessage {
    public UUID gameId;
    public long srcPlayerId;
    public long dstPlayerId;
    public InetSocketAddress localAddress;
}
