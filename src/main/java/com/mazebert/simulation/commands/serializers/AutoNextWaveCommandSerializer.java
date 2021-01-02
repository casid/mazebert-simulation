package com.mazebert.simulation.commands.serializers;

import com.mazebert.simulation.commands.AutoNextWaveCommand;
import org.jusecase.bitpack.BitReader;
import org.jusecase.bitpack.BitSerializer;
import org.jusecase.bitpack.BitWriter;

public strictfp class AutoNextWaveCommandSerializer implements BitSerializer<AutoNextWaveCommand> {

    @Override
    public AutoNextWaveCommand createObject() {
        return new AutoNextWaveCommand();
    }

    @Override
    public void serialize(BitWriter writer, AutoNextWaveCommand object) {
        writer.writeBoolean(object.autoNextWave);
    }

    @Override
    public void deserialize(BitReader reader, AutoNextWaveCommand object) {
        object.autoNextWave = reader.readBoolean();
    }
}
