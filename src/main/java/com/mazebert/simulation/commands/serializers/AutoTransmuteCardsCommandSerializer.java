package com.mazebert.simulation.commands.serializers;

import com.mazebert.simulation.commands.AutoTransmuteCardsCommand;
import org.jusecase.bitpack.BitReader;
import org.jusecase.bitpack.BitSerializer;
import org.jusecase.bitpack.BitWriter;

public strictfp class AutoTransmuteCardsCommandSerializer implements BitSerializer<AutoTransmuteCardsCommand> {

    @Override
    public AutoTransmuteCardsCommand createObject() {
        return new AutoTransmuteCardsCommand();
    }

    @Override
    public void serialize(BitWriter writer, AutoTransmuteCardsCommand object) {
        EnumSerializer.writeTowerPotionOrItemCardCategory(writer, object.cardCategory);
        EnumSerializer.writeCardType(writer, object.cardCategory, object.cardType);
        writer.writeBoolean(object.remove);
    }

    @Override
    public void deserialize(BitReader reader, AutoTransmuteCardsCommand object) {
        object.cardCategory = EnumSerializer.readTowerPotionOrItemCardCategory(reader);
        object.cardType = EnumSerializer.readCardType(reader, object.cardCategory);
        object.remove = reader.readBoolean();
    }
}
