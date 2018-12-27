package com.mazebert.simulation.commands.serializers;

import com.mazebert.simulation.commands.InitPlayerCommand;
import org.jusecase.bitpack.BitReader;
import org.jusecase.bitpack.BitSerializer;
import org.jusecase.bitpack.BitWriter;

public strictfp class InitPlayerCommandSerializer implements BitSerializer<InitPlayerCommand> {

    @Override
    public InitPlayerCommand createObject() {
        return new InitPlayerCommand();
    }

    @Override
    public void serialize(BitWriter writer, InitPlayerCommand object) {
        EnumSerializer.writeHeroType(writer, object.heroType);
    }

    @Override
    public void deserialize(BitReader reader, InitPlayerCommand object) {
        object.heroType = EnumSerializer.readHeroType(reader);
    }
}