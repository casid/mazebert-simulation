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
        if (object.potionType == null) {
            writer.writeInt12(0);
        } else {
            writer.writeInt12(object.potionType.id);
        }
        writer.writeInt12(object.towerX);
        writer.writeInt12(object.towerY);
    }

    @Override
    public void deserialize(BitReader reader, DrinkPotionCommand object) {
        object.potionType = PotionType.forId(reader.readInt12());
        object.towerX = reader.readInt12();
        object.towerY = reader.readInt12();
    }
}
