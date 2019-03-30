package com.mazebert.simulation.commands.serializers;

import com.mazebert.simulation.commands.TakeElementCardCommand;
import org.jusecase.bitpack.BitReader;
import org.jusecase.bitpack.BitSerializer;
import org.jusecase.bitpack.BitWriter;

public strictfp class TakeElementCardCommandSerializer implements BitSerializer<TakeElementCardCommand> {

    @Override
    public TakeElementCardCommand createObject() {
        return new TakeElementCardCommand();
    }

    @Override
    public void serialize(BitWriter writer, TakeElementCardCommand object) {
        EnumSerializer.writePotionType(writer, object.card);
    }

    @Override
    public void deserialize(BitReader reader, TakeElementCardCommand object) {
        object.card = EnumSerializer.readPotionType(reader);
    }
}
