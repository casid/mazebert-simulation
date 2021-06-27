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
        EnumSerializer enumSerializer = new EnumSerializer(version);

        protocol.register(new InitGameCommandSerializer(enumSerializer));
        protocol.register(new InitPlayerCommandSerializer(enumSerializer));
        protocol.register(new BuildTowerCommandSerializer(enumSerializer));
        protocol.register(new SellTowerCommandSerializer());
        protocol.register(new NextWaveCommandSerializer());
        protocol.register(new EquipItemCommandSerializer(enumSerializer));
        protocol.register(new DrinkPotionCommandSerializer(enumSerializer));
        protocol.register(new PauseCommandSerializer());
        protocol.register(new ActivateAbilityCommandSerializer(enumSerializer));
        protocol.register(new TransmuteCardsCommandSerializer(enumSerializer, version));
        protocol.register(new SendMessageCommandSerializer());
        protocol.register(new WizardPowerSerializer(enumSerializer));
        protocol.register(new QuestDataSerializer(enumSerializer));
        if (version >= Sim.v11) {
            protocol.register(new TakeElementCardCommandSerializer(enumSerializer));
        }
        if (version >= Sim.v13) {
            protocol.register(new AutoTransmuteCardsCommandSerializer(enumSerializer, version));
        }
        if (version >= Sim.v17) {
            // Actually not a command, but we need to register it here so that the ids stay in order.
            protocol.register(new LoadingProgressSerializer());
        }
        if (version >= Sim.vRoC) {
            protocol.register(new TransferCardCommandSerializer(enumSerializer));
        }
        if (version >= Sim.v23) {
            protocol.register(new AutoNextWaveCommandSerializer());
        }
    }
}