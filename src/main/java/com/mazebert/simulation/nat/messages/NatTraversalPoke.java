package com.mazebert.simulation.nat.messages;

import org.jusecase.bitnet.message.BitMessage;

public class NatTraversalPoke extends BitMessage {
    public long localSecret;
    public long remoteSecret;
}
