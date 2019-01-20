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
        bitWriter.writeInetAddress(object.dstAddress);
        bitWriter.writeInt32(object.dstPort);
        bitWriter.writeInetAddress(object.dstLocalAddress);
        bitWriter.writeInt32(object.dstLocalPort);
    }

    @Override
    public void deserialize(BitReader bitReader, NatTraversalResponse object) {
        object.gameId = bitReader.readUuidNonNull();
        object.srcPlayerId = bitReader.readLong();
        object.dstPlayerId = bitReader.readLong();
        object.dstAddress = bitReader.readInetAddress();
        object.dstPort = bitReader.readInt32();
        object.dstLocalAddress = bitReader.readInetAddress();
        object.dstLocalPort = bitReader.readInt32();
    }
}
