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
        writer.writeUnsignedInt(26, object.version);
        writer.writeUuidNonNull(object.gameId);
        writer.writeUnsignedInt12(object.rounds);
        EnumSerializer.writeDifficulty(writer, object.difficulty);
    }

    @Override
    public void deserialize(BitReader reader, InitGameCommand object) {
        object.version = reader.readUnsignedInt(26);
        object.gameId = reader.readUuidNonNull();
        object.rounds = reader.readUnsignedInt12();
        object.difficulty = EnumSerializer.readDifficulty(reader);
    }
}
