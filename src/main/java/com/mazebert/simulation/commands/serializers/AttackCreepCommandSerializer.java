package com.mazebert.simulation.commands.serializers;

import com.mazebert.simulation.commands.AttackCreepCommand;
import org.jusecase.bitpack.BitReader;
import org.jusecase.bitpack.BitSerializer;
import org.jusecase.bitpack.BitWriter;

public strictfp class AttackCreepCommandSerializer implements BitSerializer<AttackCreepCommand> {

    private static final int CREEP_ID_BITS = 12;

    @Override
    public AttackCreepCommand createObject() {
        return new AttackCreepCommand();
    }

    @Override
    public void serialize(BitWriter writer, AttackCreepCommand object) {
        writer.writeUnsignedInt(CREEP_ID_BITS, object.creepId);
    }

    @Override
    public void deserialize(BitReader reader, AttackCreepCommand object) {
        object.creepId = reader.readUnsignedInt(CREEP_ID_BITS);
    }
}
