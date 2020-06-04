package com.mazebert.simulation.commands.serializers;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.commands.AutoTransmuteCardsCommand;
import org.jusecase.bitpack.BitReader;
import org.jusecase.bitpack.BitSerializer;
import org.jusecase.bitpack.BitWriter;

public strictfp class AutoTransmuteCardsCommandSerializer implements BitSerializer<AutoTransmuteCardsCommand> {

    private final int version;

    public AutoTransmuteCardsCommandSerializer(int version) {
        this.version = version;
    }

    @Override
    public AutoTransmuteCardsCommand createObject() {
        return new AutoTransmuteCardsCommand();
    }

    @Override
    public void serialize(BitWriter writer, AutoTransmuteCardsCommand object) {
        EnumSerializer.writeTowerPotionOrItemCardCategory(writer, object.cardCategory);
        EnumSerializer.writeCardType(writer, object.cardCategory, object.cardType);
        writer.writeBoolean(object.remove);
        if (!object.remove && version >= Sim.vDoLEnd) {
            writer.writeUnsignedInt8(object.amountToKeep);
        }
    }

    @Override
    public void deserialize(BitReader reader, AutoTransmuteCardsCommand object) {
        object.cardCategory = EnumSerializer.readTowerPotionOrItemCardCategory(reader);
        object.cardType = EnumSerializer.readCardType(reader, object.cardCategory);
        object.remove = reader.readBoolean();
        if (!object.remove && version >= Sim.vDoLEnd) {
            object.amountToKeep = reader.readUnsignedInt8();
        }
    }
}
