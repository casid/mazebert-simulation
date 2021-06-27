package com.mazebert.simulation.commands.serializers;

import com.mazebert.simulation.units.quests.QuestData;
import org.jusecase.bitpack.BitReader;
import org.jusecase.bitpack.BitSerializer;
import org.jusecase.bitpack.BitWriter;

public strictfp class QuestDataSerializer implements BitSerializer<QuestData> {

    private final EnumSerializer enumSerializer;

    public QuestDataSerializer(EnumSerializer enumSerializer) {
        this.enumSerializer = enumSerializer;
    }

    @Override
    public QuestData createObject() {
        return new QuestData();
    }

    @Override
    public void serialize(BitWriter writer, QuestData object) {
        enumSerializer.writeQuestType(writer, object.type);
        writer.writeInt32(object.currentAmount);
    }

    @Override
    public void deserialize(BitReader reader, QuestData object) {
        object.type = enumSerializer.readQuestType(reader);
        object.currentAmount = reader.readInt32();
    }
}
