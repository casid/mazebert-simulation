package com.mazebert.simulation.commands.serializers;

import com.mazebert.simulation.commands.NextWaveCommand;
import org.jusecase.bitpack.BitReader;
import org.jusecase.bitpack.BitSerializer;
import org.jusecase.bitpack.BitWriter;

public strictfp class NextWaveCommandSerializer implements BitSerializer<NextWaveCommand> {

    @Override
    public NextWaveCommand createObject() {
        return new NextWaveCommand();
    }

    @Override
    public void serialize(BitWriter writer, NextWaveCommand object) {
    }

    @Override
    public void deserialize(BitReader reader, NextWaveCommand object) {
    }
}
