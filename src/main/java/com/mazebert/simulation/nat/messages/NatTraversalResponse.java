package com.mazebert.simulation.nat.messages;

import org.jusecase.bitnet.message.BitMessage;

import java.net.InetAddress;
import java.util.UUID;

public class NatTraversalResponse extends BitMessage {
    public UUID gameId;
    public long srcPlayerId;
    public long dstPlayerId;
    public InetAddress dstAddress;
    public int dstPort;
}
