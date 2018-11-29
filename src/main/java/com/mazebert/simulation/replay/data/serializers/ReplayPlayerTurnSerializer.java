package com.mazebert.simulation.replay.data.serializers;

import com.mazebert.simulation.replay.data.ReplayTurn;
import org.jusecase.bitpack.BitReader;
import org.jusecase.bitpack.BitSerializer;
import org.jusecase.bitpack.BitWriter;

public strictfp class ReplayPlayerTurnSerializer implements BitSerializer<ReplayTurn> {
    @Override
    public ReplayTurn createObject() {
        return new ReplayTurn();
    }

    @Override
    public void serialize(BitWriter writer, ReplayTurn object) {
        writer.writeInt32(object.hash);
        writer.writeObjectsWithDifferentTypes(object.commands);
    }

    @Override
    public void deserialize(BitReader reader, ReplayTurn object) {
        object.hash = reader.readInt32();
        object.commands = reader.readObjectsWithDifferentTypesAsList();
    }
}