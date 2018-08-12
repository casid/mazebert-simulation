package com.mazebert.simulation.commands.serializers;

import com.mazebert.simulation.commands.InitGameCommand;
import org.jusecase.bitpack.BitReader;
import org.jusecase.bitpack.BitSerializer;
import org.jusecase.bitpack.BitWriter;

public strictfp class InitGameCommandSerializer implements BitSerializer<InitGameCommand> {

    @Override
    public InitGameCommand createObject() {
        return new InitGameCommand();
    }

    @Override
    public void serialize(BitWriter writer, InitGameCommand object) {
        writer.writeInt32(object.randomSeed);
    }

    @Override
    public void deserialize(BitReader reader, InitGameCommand object) {
        object.randomSeed = reader.readInt32();
    }
}
