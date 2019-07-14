package com.mazebert.simulation.messages.serializers;

import com.mazebert.simulation.messages.LoadingProgress;
import com.mazebert.simulation.messages.ResendTurn;
import org.jusecase.bitpack.BitReader;
import org.jusecase.bitpack.BitSerializer;
import org.jusecase.bitpack.BitWriter;

public strictfp class LoadingProgressSerializer implements BitSerializer<LoadingProgress> {
    @Override
    public LoadingProgress createObject() {
        return new LoadingProgress();
    }

    @Override
    public void serialize(BitWriter writer, LoadingProgress object) {
        writer.writeInt8(object.progress);
    }

    @Override
    public void deserialize(BitReader reader, LoadingProgress object) {
        object.progress = reader.readInt8();
    }
}
