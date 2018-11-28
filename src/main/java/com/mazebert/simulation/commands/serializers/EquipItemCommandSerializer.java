package com.mazebert.simulation.commands.serializers;

import com.mazebert.simulation.commands.EquipItemCommand;
import com.mazebert.simulation.units.items.ItemType;
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
        if (object.itemType == null) {
            writer.writeInt12(0);
        } else {
            writer.writeInt12(object.itemType.id);
        }
        writer.writeInt12(object.towerX);
        writer.writeInt12(object.towerY);
        writer.writeInt8(object.inventoryIndex);
    }

    @Override
    public void deserialize(BitReader reader, EquipItemCommand object) {
        object.itemType = ItemType.forId(reader.readInt12());
        object.towerX = reader.readInt12();
        object.towerY = reader.readInt12();
        object.inventoryIndex = reader.readInt8();
    }
}
