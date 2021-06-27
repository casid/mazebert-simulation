package com.mazebert.simulation.commands.serializers;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.commands.InitGameCommand;
import org.jusecase.bitpack.BitReader;
import org.jusecase.bitpack.BitSerializer;
import org.jusecase.bitpack.BitWriter;

public strictfp class InitGameCommandSerializer implements BitSerializer<InitGameCommand> {

    private final EnumSerializer enumSerializer;

    public InitGameCommandSerializer(EnumSerializer enumSerializer) {
        this.enumSerializer = enumSerializer;
    }

    @Override
    public InitGameCommand createObject() {
        return new InitGameCommand();
    }

    @Override
    public void serialize(BitWriter writer, InitGameCommand object) {
        writer.writeUnsignedInt(26, object.version);
        writer.writeUuidNonNull(object.gameId);
        if (object.version >= Sim.vDoL) {
            writer.writeLong(object.timestamp);
        }
        writer.writeUnsignedInt12(object.rounds);
        enumSerializer.writeDifficulty(writer, object.difficulty);
        enumSerializer.writeMapType(writer, object.map);
        writer.writeBoolean(object.tutorial);
    }

    @Override
    public void deserialize(BitReader reader, InitGameCommand object) {
        object.version = reader.readUnsignedInt(26);
        object.gameId = reader.readUuidNonNull();
        if (object.version >= Sim.vDoL) {
            object.timestamp = reader.readLong();
        }
        object.rounds = reader.readUnsignedInt12();
        object.difficulty = enumSerializer.readDifficulty(reader);
        object.map = enumSerializer.readMapType(reader);
        object.tutorial = reader.readBoolean();
    }
}
