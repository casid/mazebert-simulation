package com.mazebert.simulation.replay;

import com.mazebert.simulation.SimulationMessageProtocol;
import com.mazebert.simulation.replay.data.serializers.ReplayHeaderSerializer;
import com.mazebert.simulation.replay.data.serializers.ReplayPlayerTurnSerializer;
import com.mazebert.simulation.replay.data.serializers.ReplayTurnSerializer;
import org.jusecase.bitpack.AbstractBitProtocol;

public strictfp class ReplayProtocol extends AbstractBitProtocol {
    public ReplayProtocol() {
        register(new ReplayHeaderSerializer());
        register(new ReplayTurnSerializer());
        register(new ReplayPlayerTurnSerializer());

        SimulationMessageProtocol.registerCommands(this);
    }
}
