package com.mazebert.simulation.replay;

import com.mazebert.simulation.SimulationMessageProtocol;
import com.mazebert.simulation.replay.data.serializers.ReplayFrameSerializer;
import com.mazebert.simulation.replay.data.serializers.ReplayHeaderSerializer;
import com.mazebert.simulation.replay.data.serializers.ReplayPlayerTurnSerializer;
import org.jusecase.bitpack.AbstractBitProtocol;

public strictfp class ReplayProtocol extends AbstractBitProtocol {
    public ReplayProtocol() {
        register(new ReplayHeaderSerializer());
        register(new ReplayFrameSerializer());
        register(new ReplayPlayerTurnSerializer());

        SimulationMessageProtocol.registerCommands(this);
    }
}
