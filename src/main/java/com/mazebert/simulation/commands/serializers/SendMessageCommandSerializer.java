package com.mazebert.simulation.commands.serializers;

import com.mazebert.simulation.commands.SendMessageCommand;
import org.jusecase.bitpack.BitReader;
import org.jusecase.bitpack.BitSerializer;
import org.jusecase.bitpack.BitWriter;

public strictfp class SendMessageCommandSerializer implements BitSerializer<SendMessageCommand> {

    @Override
    public SendMessageCommand createObject() {
        return new SendMessageCommand();
    }

    @Override
    public void serialize(BitWriter writer, SendMessageCommand object) {
        writer.writeStringNonNull(10, object.message);
    }

    @Override
    public void deserialize(BitReader reader, SendMessageCommand object) {
        object.message = reader.readStringNonNull(10);
    }
}
