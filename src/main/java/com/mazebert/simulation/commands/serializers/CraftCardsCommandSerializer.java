package com.mazebert.simulation.commands.serializers;

import com.mazebert.simulation.commands.CraftCardsCommand;
import org.jusecase.bitpack.BitReader;
import org.jusecase.bitpack.BitSerializer;
import org.jusecase.bitpack.BitWriter;

public strictfp class CraftCardsCommandSerializer implements BitSerializer<CraftCardsCommand> {

    @Override
    public CraftCardsCommand createObject() {
        return new CraftCardsCommand();
    }

    @Override
    public void serialize(BitWriter writer, CraftCardsCommand object) {
        EnumSerializer.writeTowerPotionOrItemCardCategory(writer, object.cardCategory);
        EnumSerializer.writeCardType(writer, object.cardCategory, object.cardType);
        writer.writeBoolean(object.all);
    }

    @Override
    public void deserialize(BitReader reader, CraftCardsCommand object) {
        object.cardCategory = EnumSerializer.readTowerPotionOrItemCardCategory(reader);
        object.cardType = EnumSerializer.readCardType(reader, object.cardCategory);
        object.all = reader.readBoolean();
    }
}
