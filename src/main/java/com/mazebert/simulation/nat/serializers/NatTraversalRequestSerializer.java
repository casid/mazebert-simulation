package com.mazebert.simulation.nat.serializers;

import com.mazebert.simulation.nat.messages.NatTraversalRequest;
import org.jusecase.bitpack.BitReader;
import org.jusecase.bitpack.BitSerializer;
import org.jusecase.bitpack.BitWriter;

public class NatTraversalRequestSerializer implements BitSerializer<NatTraversalRequest> {
    @Override
    public NatTraversalRequest createObject() {
        return new NatTraversalRequest();
    }

    @Override
    public void serialize(BitWriter bitWriter, NatTraversalRequest object) {
        bitWriter.writeUuidNonNull(object.gameId);
        bitWriter.writeLong(object.srcPlayerId);
        bitWriter.writeLong(object.dstPlayerId);
    }

    @Override
    public void deserialize(BitReader bitReader, NatTraversalRequest object) {
        object.gameId = bitReader.readUuidNonNull();
        object.srcPlayerId = bitReader.readLong();
        object.dstPlayerId = bitReader.readLong();
    }
}
