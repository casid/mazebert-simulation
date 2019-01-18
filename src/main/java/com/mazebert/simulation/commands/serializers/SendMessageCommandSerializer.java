package com.mazebert.simulation.commands.serializers;

import com.mazebert.simulation.commands.SendMessageCommand;
import org.jusecase.bitpack.BitReader;
import org.jusecase.bitpack.BitSerializer;
import org.jusecase.bitpack.BitWriter;

@SuppressWarnings("unused") // TODO add to simulation, it is only used in lobby so far... (but do no add those commands to the replay!! maybe boolean isIncludedInReplay() in Command base class?)
public strictfp class SendMessageCommandSerializer implements BitSerializer<SendMessageCommand> {

    @Override
    public SendMessageCommand createObject() {
        return new SendMessageCommand();
    }

    @Override
    public void serialize(BitWriter writer, SendMessageCommand object) {
        writer.writeStringNonNull(object.message);
    }

    @Override
    public void deserialize(BitReader reader, SendMessageCommand object) {
        object.message = reader.readStringNonNull();
    }
}
