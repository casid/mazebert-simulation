package com.mazebert.simulation.replay.data.serializers;

import com.mazebert.simulation.replay.data.ReplayTurn;
import org.jusecase.bitpack.BitReader;
import org.jusecase.bitpack.BitSerializer;
import org.jusecase.bitpack.BitWriter;

public strictfp class ReplayTurnSerializer implements BitSerializer<ReplayTurn> {
    @Override
    public ReplayTurn createObject() {
        return new ReplayTurn();
    }

    @Override
    public void serialize(BitWriter writer, ReplayTurn object) {
        writer.writeObjectsWithSameType(object.playerTurns);
    }

    @Override
    public void deserialize(BitReader reader, ReplayTurn object) {
        object.playerTurns = reader.readObjectsWithDifferentTypesAsList();
    }
}
