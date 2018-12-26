package com.mazebert.simulation.commands.serializers;

import com.mazebert.simulation.commands.TradeCardsCommand;
import org.jusecase.bitpack.BitReader;
import org.jusecase.bitpack.BitSerializer;
import org.jusecase.bitpack.BitWriter;

public strictfp class TradeCardsCommandSerializer implements BitSerializer<TradeCardsCommand> {

    @Override
    public TradeCardsCommand createObject() {
        return new TradeCardsCommand();
    }

    @Override
    public void serialize(BitWriter writer, TradeCardsCommand object) {
        EnumSerializer.writeTowerPotionOrItemCardCategory(writer, object.cardCategory);
        EnumSerializer.writeCardType(writer, object.cardCategory, object.cardType);
        writer.writeBoolean(object.all);
    }

    @Override
    public void deserialize(BitReader reader, TradeCardsCommand object) {
        object.cardCategory = EnumSerializer.readTowerPotionOrItemCardCategory(reader);
        object.cardType = EnumSerializer.readCardType(reader, object.cardCategory);
        object.all = reader.readBoolean();
    }
}
