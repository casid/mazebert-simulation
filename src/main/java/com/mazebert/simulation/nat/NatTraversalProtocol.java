package com.mazebert.simulation.nat;

import com.mazebert.simulation.nat.serializers.NatTraversalPokeSerializer;
import com.mazebert.simulation.nat.serializers.NatTraversalRequestSerializer;
import com.mazebert.simulation.nat.serializers.NatTraversalResponseSerializer;
import org.jusecase.bitnet.message.BitMessageProtocol;

@SuppressWarnings("unused") // by server and client
public class NatTraversalProtocol extends BitMessageProtocol {

    public static final int ID = 283578937;
    public static final int MAX_PACKET_BYTES = 256;
    public static final int MAX_PACKETS_PER_MESSAGE = 1;

    public NatTraversalProtocol() {
        super(MAX_PACKET_BYTES, MAX_PACKETS_PER_MESSAGE);

        register(new NatTraversalRequestSerializer());
        register(new NatTraversalResponseSerializer());
        register(new NatTraversalPokeSerializer());
    }
}