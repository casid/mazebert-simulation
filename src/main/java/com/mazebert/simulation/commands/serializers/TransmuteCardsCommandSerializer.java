package com.mazebert.simulation.commands.serializers;

import com.mazebert.simulation.commands.TransmuteCardsCommand;
import org.jusecase.bitpack.BitReader;
import org.jusecase.bitpack.BitSerializer;
import org.jusecase.bitpack.BitWriter;

public strictfp class TransmuteCardsCommandSerializer implements BitSerializer<TransmuteCardsCommand> {

    @Override
    public TransmuteCardsCommand createObject() {
        return new TransmuteCardsCommand();
    }

    @Override
    public void serialize(BitWriter writer, TransmuteCardsCommand object) {
        EnumSerializer.writeTowerPotionOrItemCardCategory(writer, object.cardCategory);
        EnumSerializer.writeCardType(writer, object.cardCategory, object.cardType);
        writer.writeBoolean(object.all);
    }

    @Override
    public void deserialize(BitReader reader, TransmuteCardsCommand object) {
        object.cardCategory = EnumSerializer.readTowerPotionOrItemCardCategory(reader);
        object.cardType = EnumSerializer.readCardType(reader, object.cardCategory);
        object.all = reader.readBoolean();
    }
}
