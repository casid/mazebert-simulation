package com.mazebert.simulation.replay.data.serializers;

import com.mazebert.simulation.Sim;
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
        writer.writeInt32(ReplayHeader.FORMAT_IDENTIFIER);

        writer.writeUnsignedInt(26, object.version);
        if (object.version >= Sim.v17) {
            writer.writeBoolean(object.season);
        }

        writer.writeUnsignedInt3(object.playerId);
        writer.writeUnsignedInt3(object.playerCount);
    }

    @Override
    public void deserialize(BitReader reader, ReplayHeader object) {
        int formatIdentifier = reader.readInt32();
        if (formatIdentifier != ReplayHeader.FORMAT_IDENTIFIER) {
            throw new IllegalStateException("Unsupported game format");
        }

        object.version = reader.readUnsignedInt(26);
        if (object.version >= Sim.v17) {
            object.season = reader.readBoolean();
        }

        object.playerId = reader.readUnsignedInt3();
        object.playerCount = reader.readUnsignedInt3();
    }
}
