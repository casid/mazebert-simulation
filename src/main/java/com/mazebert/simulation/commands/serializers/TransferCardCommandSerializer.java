package com.mazebert.simulation.commands.serializers;

import com.mazebert.simulation.commands.TransferCardCommand;
import org.jusecase.bitpack.BitReader;
import org.jusecase.bitpack.BitSerializer;
import org.jusecase.bitpack.BitWriter;

public strictfp class TransferCardCommandSerializer implements BitSerializer<TransferCardCommand> {

    private final EnumSerializer enumSerializer;

    public TransferCardCommandSerializer(EnumSerializer enumSerializer) {
        this.enumSerializer = enumSerializer;
    }

    @Override
    public TransferCardCommand createObject() {
        return new TransferCardCommand();
    }

    @Override
    public void serialize(BitWriter writer, TransferCardCommand object) {
        writer.writeInt8(object.toPlayerId);
        enumSerializer.writeTowerPotionOrItemCardCategory(writer, object.cardCategory);
        enumSerializer.writeCardType(writer, object.cardCategory, object.cardType);
    }

    @Override
    public void deserialize(BitReader reader, TransferCardCommand object) {
        object.toPlayerId = reader.readInt8();
        object.cardCategory = enumSerializer.readTowerPotionOrItemCardCategory(reader);
        object.cardType = enumSerializer.readCardType(reader, object.cardCategory);
    }
}
