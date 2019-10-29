package com.mazebert.simulation;

import com.mazebert.simulation.commands.serializers.*;
import com.mazebert.simulation.messages.serializers.LoadingProgressSerializer;
import com.mazebert.simulation.messages.serializers.ResendTurnSerializer;
import com.mazebert.simulation.messages.serializers.TurnSerializer;
import org.jusecase.bitnet.message.BitMessageProtocol;
import org.jusecase.bitpack.BitProtocol;

public strictfp class SimulationMessageProtocol extends BitMessageProtocol {

    public static final int MAX_PACKET_BYTES = 1400;
    public static final int MAX_PACKETS_PER_MESSAGE = 10;

    public SimulationMessageProtocol(int version) {
        super(MAX_PACKET_BYTES, MAX_PACKETS_PER_MESSAGE);
        register(new TurnSerializer());
        register(new ResendTurnSerializer());

        registerCommands(this, version);
    }

    public static void registerCommands(BitProtocol protocol, int version) {
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
        protocol.register(new QuestDataSerializer());
        if (version >= Sim.v11) {
            protocol.register(new TakeElementCardCommandSerializer());
        }
        if (version >= Sim.v13) {
            protocol.register(new AutoTransmuteCardsCommandSerializer());
        }
        if (version >= Sim.v17) {
            // Actually not a command, but we need to register it here so that the ids stay in order.
            protocol.register(new LoadingProgressSerializer());
        }
    }
}