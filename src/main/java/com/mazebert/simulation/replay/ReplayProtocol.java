package com.mazebert.simulation.replay;

import com.mazebert.simulation.replay.data.serializers.ReplayHeaderSerializer;
import com.mazebert.simulation.replay.data.serializers.ReplayTurnSerializer;
import org.jusecase.bitpack.AbstractBitProtocol;

public strictfp class ReplayProtocol extends AbstractBitProtocol {
    public static final int MAX_BYTES_PER_ENTITY = 10000;

    public ReplayProtocol() {
        register(new ReplayHeaderSerializer());
        register(new ReplayTurnSerializer());
    }
}
