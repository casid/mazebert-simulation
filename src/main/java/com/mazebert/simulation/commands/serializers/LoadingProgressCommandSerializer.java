package com.mazebert.simulation.commands.serializers;

import com.mazebert.simulation.commands.LoadingProgressCommand;
import org.jusecase.bitpack.BitReader;
import org.jusecase.bitpack.BitSerializer;
import org.jusecase.bitpack.BitWriter;

public strictfp class LoadingProgressCommandSerializer implements BitSerializer<LoadingProgressCommand> {

    @Override
    public LoadingProgressCommand createObject() {
        return new LoadingProgressCommand();
    }

    @Override
    public void serialize(BitWriter writer, LoadingProgressCommand object) {
        writer.writeInt8(object.progress);
    }

    @Override
    public void deserialize(BitReader reader, LoadingProgressCommand object) {
        object.progress = reader.readInt8();
    }
}
