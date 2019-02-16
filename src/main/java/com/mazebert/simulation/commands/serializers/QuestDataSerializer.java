package com.mazebert.simulation.commands.serializers;

import com.mazebert.simulation.units.quests.QuestData;
import org.jusecase.bitpack.BitReader;
import org.jusecase.bitpack.BitSerializer;
import org.jusecase.bitpack.BitWriter;

public class QuestDataSerializer implements BitSerializer<QuestData> {
    @Override
    public QuestData createObject() {
        return new QuestData();
    }

    @Override
    public void serialize(BitWriter writer, QuestData object) {
        EnumSerializer.writeQuestType(writer, object.type);
        writer.writeUnsignedInt16(object.currentAmount);
    }

    @Override
    public void deserialize(BitReader reader, QuestData object) {
        object.type = EnumSerializer.readQuestType(reader);
        object.currentAmount = reader.readUnsignedInt16();
    }
}
