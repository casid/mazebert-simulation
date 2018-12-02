package com.mazebert.simulation.commands.serializers;

import com.mazebert.simulation.commands.PauseCommand;
import org.jusecase.bitpack.BitReader;
import org.jusecase.bitpack.BitSerializer;
import org.jusecase.bitpack.BitWriter;

public strictfp class PauseCommandSerializer implements BitSerializer<PauseCommand> {

    @Override
    public PauseCommand createObject() {
        return new PauseCommand();
    }

    @Override
    public void serialize(BitWriter writer, PauseCommand object) {
        writer.writeBoolean(object.pause);
    }

    @Override
    public void deserialize(BitReader reader, PauseCommand object) {
        object.pause = reader.readBoolean();
    }
}
