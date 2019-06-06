package com.mazebert.simulation.replay.data.serializers;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.replay.data.ReplayHeader;
import org.jusecase.bitpack.BitReader;
import org.jusecase.bitpack.BitSerializer;
import org.jusecase.bitpack.BitWriter;

/**
 * Caution: The first part of the header must be exactly 64 bit (8 byte) long,
 * it helps to easily extract this information when games are uploaded!
 * Additional information needs to be added after that part.
 */
public strictfp class ReplayHeaderSerializer implements BitSerializer<ReplayHeader> {
    @Override
    public ReplayHeader createObject() {
        return new ReplayHeader();
    }

    @Override
    public void serialize(BitWriter writer, ReplayHeader object) {
        // First 32 bits - format identifier
        writer.writeInt32(ReplayHeader.FORMAT_IDENTIFIER);

        // Next 32 bits - version & player info
        writer.writeUnsignedInt(26, object.version);
        writer.writeUnsignedInt3(object.playerId);
        writer.writeUnsignedInt3(object.playerCount);

        // Additional information
        if (object.version >= Sim.v17DoL) {
            writer.writeBoolean(object.season);
        }
    }

    @Override
    public void deserialize(BitReader reader, ReplayHeader object) {
        deserializeFirst8Bytes(reader, object);

        // Additional information
        if (object.version >= Sim.v17DoL) {
            object.season = reader.readBoolean();
        }
    }

    public void deserializeFirst8Bytes(BitReader reader, ReplayHeader object) {
        // First 32 bits - format identifier
        int formatIdentifier = reader.readInt32();
        if (formatIdentifier != ReplayHeader.FORMAT_IDENTIFIER) {
            throw new IllegalStateException("Unsupported game format");
        }

        // Next 32 bits - version & player info
        object.version = reader.readUnsignedInt(26);
        object.playerId = reader.readUnsignedInt3();
        object.playerCount = reader.readUnsignedInt3();
    }
}
