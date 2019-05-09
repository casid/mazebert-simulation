package com.mazebert.simulation.messages.serializers;

import com.mazebert.simulation.messages.Turn;
import org.jusecase.bitpack.BitWriter;
import org.jusecase.bitpack.BitSerializer;
import org.jusecase.bitpack.BitReader;

public strictfp class TurnSerializer implements BitSerializer<Turn> {

    public static final int MAX_COMMANDS = 15; // 4  bit unsigned integer

    @Override
    public Turn createObject() {
        return new Turn();
    }

    @Override
    public void serialize(BitWriter writer, Turn object) {
        writer.writeInt8(object.playerId);
        writer.writeInt32(object.turnNumber);
        writer.writeInt32(object.hash);
        writer.writeObjectsWithDifferentTypes(4, object.commands);
    }

    @Override
    public void deserialize(BitReader reader, Turn object) {
        object.playerId = reader.readInt8();
        object.turnNumber = reader.readInt32();
        object.hash = reader.readInt32();
        object.commands = reader.readObjectsWithDifferentTypesAsList(4);
    }
}
