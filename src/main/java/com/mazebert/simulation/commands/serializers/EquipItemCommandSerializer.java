package com.mazebert.simulation.commands.serializers;

import com.mazebert.simulation.commands.EquipItemCommand;
import org.jusecase.bitpack.BitReader;
import org.jusecase.bitpack.BitSerializer;
import org.jusecase.bitpack.BitWriter;

public strictfp class EquipItemCommandSerializer implements BitSerializer<EquipItemCommand> {

    @Override
    public EquipItemCommand createObject() {
        return new EquipItemCommand();
    }

    @Override
    public void serialize(BitWriter writer, EquipItemCommand object) {
        EnumSerializer.writeItemType(writer, object.itemType);
        writer.writeInt8(object.towerX);
        writer.writeInt8(object.towerY);
        writer.writeUnsignedInt3(object.inventoryIndex);
    }

    @Override
    public void deserialize(BitReader reader, EquipItemCommand object) {
        object.itemType = EnumSerializer.readItemType(reader);
        object.towerX = reader.readInt8();
        object.towerY = reader.readInt8();
        object.inventoryIndex = reader.readUnsignedInt3();
    }
}
