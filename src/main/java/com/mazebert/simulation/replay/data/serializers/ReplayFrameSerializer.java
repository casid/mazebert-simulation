package com.mazebert.simulation.replay.data.serializers;

import com.mazebert.simulation.replay.data.ReplayFrame;
import com.mazebert.simulation.replay.data.ReplayTurn;
import org.jusecase.bitpack.BitReader;
import org.jusecase.bitpack.BitSerializer;
import org.jusecase.bitpack.BitWriter;

public strictfp class ReplayFrameSerializer implements BitSerializer<ReplayFrame> {
    @Override
    public ReplayFrame createObject() {
        return new ReplayFrame();
    }

    @Override
    public void serialize(BitWriter writer, ReplayFrame object) {
        writer.writeInt32(object.turnNumber);
        writer.writeInt32(object.hash);
        writer.writeObjectsWithSameType(3, object.playerTurns);
    }

    @Override
    public void deserialize(BitReader reader, ReplayFrame object) {
        object.turnNumber = reader.readInt32();
        object.hash = reader.readInt32();
        object.playerTurns = reader.readObjectsWithSameTypeAsArray(3, ReplayTurn.class);
    }
}
