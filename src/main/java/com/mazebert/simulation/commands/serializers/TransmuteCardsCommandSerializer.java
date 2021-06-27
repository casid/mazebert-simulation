package com.mazebert.simulation.commands.serializers;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.commands.TransmuteCardsCommand;
import org.jusecase.bitpack.BitReader;
import org.jusecase.bitpack.BitSerializer;
import org.jusecase.bitpack.BitWriter;

public strictfp class TransmuteCardsCommandSerializer implements BitSerializer<TransmuteCardsCommand> {

    private final EnumSerializer enumSerializer;
    private final int version;

    public TransmuteCardsCommandSerializer(EnumSerializer enumSerializer, int version) {
        this.enumSerializer = enumSerializer;
        this.version = version;
    }

    @Override
    public TransmuteCardsCommand createObject() {
        return new TransmuteCardsCommand();
    }

    @Override
    public void serialize(BitWriter writer, TransmuteCardsCommand object) {
        enumSerializer.writeTowerPotionOrItemCardCategory(writer, object.cardCategory);
        enumSerializer.writeCardType(writer, object.cardCategory, object.cardType);
        writer.writeBoolean(object.all);
        if (object.all && version >= Sim.vDoLEnd) {
            writer.writeUnsignedInt8(object.amountToKeep);
        }
    }

    @Override
    public void deserialize(BitReader reader, TransmuteCardsCommand object) {
        object.cardCategory = enumSerializer.readTowerPotionOrItemCardCategory(reader);
        object.cardType = enumSerializer.readCardType(reader, object.cardCategory);
        object.all = reader.readBoolean();
        if (object.all && version >= Sim.vDoLEnd) {
            object.amountToKeep = reader.readUnsignedInt8();
        }
    }
}
