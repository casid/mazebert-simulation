package com.mazebert.simulation.nat.serializers;

import com.mazebert.simulation.nat.messages.NatTraversalPoke;
import org.jusecase.bitpack.BitReader;
import org.jusecase.bitpack.BitSerializer;
import org.jusecase.bitpack.BitWriter;

public class NatTraversalPokeSerializer implements BitSerializer<NatTraversalPoke> {
    @Override
    public NatTraversalPoke createObject() {
        return new NatTraversalPoke();
    }

    @Override
    public void serialize(BitWriter bitWriter, NatTraversalPoke object) {
        bitWriter.writeLong(object.localSecret);
        bitWriter.writeLong(object.remoteSecret);
    }

    @Override
    public void deserialize(BitReader bitReader, NatTraversalPoke object) {
        object.localSecret = bitReader.readLong();
        object.remoteSecret = bitReader.readLong();
    }
}
