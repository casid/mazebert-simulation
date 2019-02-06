package com.mazebert.simulation;

import com.mazebert.simulation.commands.serializers.*;
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
        protocol.register(new InitPlayerCommandSerializer());
        protocol.register(new BuildTowerCommandSerializer());
        protocol.register(new SellTowerCommandSerializer());
        protocol.register(new NextWaveCommandSerializer());
        protocol.register(new EquipItemCommandSerializer());
        protocol.register(new DrinkPotionCommandSerializer());
        protocol.register(new PauseCommandSerializer());
        protocol.register(new ActivateAbilityCommandSerializer());
        protocol.register(new TransmuteCardsCommandSerializer());
        protocol.register(new SendMessageCommandSerializer());
        protocol.register(new WizardPowerSerializer());
    }
}