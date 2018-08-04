package com.mazebert.simulation.replay.data.serializers;

import com.mazebert.simulation.replay.data.ReplayHeader;
import org.jusecase.bitpack.BitReader;
import org.jusecase.bitpack.BitSerializer;
import org.jusecase.bitpack.BitWriter;

public strictfp class ReplayHeaderSerializer implements BitSerializer<ReplayHeader> {
    @Override
    public ReplayHeader createObject() {
        return new ReplayHeader();
    }

    @Override
    public void serialize(BitWriter writer, ReplayHeader object) {
        writer.writeInt8(object.playerCount);
    }

    @Override
    public void deserialize(BitReader reader, ReplayHeader object) {
        object.playerCount = reader.readInt8();
    }
}
