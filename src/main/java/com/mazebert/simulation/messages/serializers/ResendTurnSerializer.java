package com.mazebert.simulation.messages.serializers;

import com.mazebert.simulation.messages.ResendTurn;
import org.jusecase.bitpack.BitWriter;
import org.jusecase.bitpack.BitSerializer;
import org.jusecase.bitpack.BitReader;

public strictfp class ResendTurnSerializer implements BitSerializer<ResendTurn> {
    @Override
    public ResendTurn createObject() {
        return new ResendTurn();
    }

    @Override
    public void serialize(BitWriter writer, ResendTurn object) {
        writer.writeInt32(object.turnNumber);
    }

    @Override
    public void deserialize(BitReader reader, ResendTurn object) {
        object.turnNumber = reader.readInt32();
    }
}
