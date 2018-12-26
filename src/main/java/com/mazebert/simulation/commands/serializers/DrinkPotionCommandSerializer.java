package com.mazebert.simulation.commands.serializers;

import com.mazebert.simulation.commands.DrinkPotionCommand;
import com.mazebert.simulation.units.potions.PotionType;
import org.jusecase.bitpack.BitReader;
import org.jusecase.bitpack.BitSerializer;
import org.jusecase.bitpack.BitWriter;

public strictfp class DrinkPotionCommandSerializer implements BitSerializer<DrinkPotionCommand> {

    @Override
    public DrinkPotionCommand createObject() {
        return new DrinkPotionCommand();
    }

    @Override
    public void serialize(BitWriter writer, DrinkPotionCommand object) {
        EnumSerializer.writePotionType(writer, object.potionType);
        writer.writeInt8(object.towerX);
        writer.writeInt8(object.towerY);
    }

    @Override
    public void deserialize(BitReader reader, DrinkPotionCommand object) {
        object.potionType = EnumSerializer.readPotionType(reader);
        object.towerX = reader.readInt8();
        object.towerY = reader.readInt8();
    }
}
