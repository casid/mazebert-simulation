package com.mazebert.simulation.commands.serializers;

import com.mazebert.simulation.commands.SellTowerCommand;
import org.jusecase.bitpack.BitReader;
import org.jusecase.bitpack.BitSerializer;
import org.jusecase.bitpack.BitWriter;

public strictfp class SellTowerCommandSerializer implements BitSerializer<SellTowerCommand> {

    @Override
    public SellTowerCommand createObject() {
        return new SellTowerCommand();
    }

    @Override
    public void serialize(BitWriter writer, SellTowerCommand object) {
        writer.writeInt12(object.x);
        writer.writeInt12(object.y);
    }

    @Override
    public void deserialize(BitReader reader, SellTowerCommand object) {
        object.x = reader.readInt12();
        object.y = reader.readInt12();
    }
}
