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
        // Version must be the first element of the header!
        writer.writeStringNonNull(object.version);
        writer.writeUnsignedInt3(object.playerId);
        writer.writeUnsignedInt3(object.playerCount);
    }

    @Override
    public void deserialize(BitReader reader, ReplayHeader object) {
        object.version = reader.readStringNonNull();
        object.playerId = reader.readUnsignedInt3();
        object.playerCount = reader.readUnsignedInt3();
    }
}
