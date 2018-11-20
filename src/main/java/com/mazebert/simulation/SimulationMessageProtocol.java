package com.mazebert.simulation;

import com.mazebert.simulation.commands.serializers.BuildTowerCommandSerializer;
import com.mazebert.simulation.commands.serializers.EquipItemCommandSerializer;
import com.mazebert.simulation.commands.serializers.InitGameCommandSerializer;
import com.mazebert.simulation.commands.serializers.NextWaveCommandSerializer;
import com.mazebert.simulation.messages.serializers.ResendTurnSerializer;
import com.mazebert.simulation.messages.serializers.TurnSerializer;
import org.jusecase.bitnet.message.BitMessageProtocol;
import org.jusecase.bitpack.BitProtocol;

public strictfp class SimulationMessageProtocol extends BitMessageProtocol {

    public static final int MAX_PACKET_BYTES = 1400;
    public static final int MAX_PACKETS_PER_MESSAGE = 10;

    public SimulationMessageProtocol() {
        super(MAX_PACKET_BYTES, MAX_PACKETS_PER_MESSAGE);
        register(new TurnSerializer());
        register(new ResendTurnSerializer());

        registerCommands(this);
    }

    public static void registerCommands(BitProtocol protocol) {
        protocol.register(new InitGameCommandSerializer());
        protocol.register(new BuildTowerCommandSerializer());
        protocol.register(new NextWaveCommandSerializer());
        protocol.register(new EquipItemCommandSerializer());
    }
}