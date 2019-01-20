package com.mazebert.simulation.nat.serializers;

import com.mazebert.simulation.nat.messages.NatTraversalResponse;
import org.jusecase.bitpack.BitReader;
import org.jusecase.bitpack.BitSerializer;
import org.jusecase.bitpack.BitWriter;

public class NatTraversalResponseSerializer implements BitSerializer<NatTraversalResponse> {
    @Override
    public NatTraversalResponse createObject() {
        return new NatTraversalResponse();
    }

    @Override
    public void serialize(BitWriter bitWriter, NatTraversalResponse object) {
        bitWriter.writeUuidNonNull(object.gameId);
        bitWriter.writeLong(object.srcPlayerId);
        bitWriter.writeLong(object.dstPlayerId);
        bitWriter.writeInetSocketAddress(object.dstLocalAddress);
        bitWriter.writeInetSocketAddress(object.dstRemoteAddress);
    }

    @Override
    public void deserialize(BitReader bitReader, NatTraversalResponse object) {
        object.gameId = bitReader.readUuidNonNull();
        object.srcPlayerId = bitReader.readLong();
        object.dstPlayerId = bitReader.readLong();
        object.dstLocalAddress = bitReader.readInetSocketAddress();
        object.dstRemoteAddress = bitReader.readInetSocketAddress();
    }
}
