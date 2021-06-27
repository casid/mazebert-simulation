package com.mazebert.simulation.commands.serializers;

import com.mazebert.simulation.commands.DrinkPotionCommand;
import org.jusecase.bitpack.BitReader;
import org.jusecase.bitpack.BitSerializer;
import org.jusecase.bitpack.BitWriter;

public strictfp class DrinkPotionCommandSerializer implements BitSerializer<DrinkPotionCommand> {

    private final EnumSerializer enumSerializer;

    public DrinkPotionCommandSerializer(EnumSerializer enumSerializer) {
        this.enumSerializer = enumSerializer;
    }

    @Override
    public DrinkPotionCommand createObject() {
        return new DrinkPotionCommand();
    }

    @Override
    public void serialize(BitWriter writer, DrinkPotionCommand object) {
        enumSerializer.writePotionType(writer, object.potionType);
        writer.writeInt8(object.towerX);
        writer.writeInt8(object.towerY);
        writer.writeBoolean(object.all);
    }

    @Override
    public void deserialize(BitReader reader, DrinkPotionCommand object) {
        object.potionType = enumSerializer.readPotionType(reader);
        object.towerX = reader.readInt8();
        object.towerY = reader.readInt8();
        object.all = reader.readBoolean();
    }
}
