package com.mazebert.simulation.commands.serializers;

import com.mazebert.simulation.commands.AttackCreepCommand;
import com.mazebert.simulation.units.creeps.Creep;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.jusecase.bitpack.AbstractBitProtocol;
import org.jusecase.bitpack.BitProtocol;
import org.jusecase.bitpack.buffer.BufferBitReader;
import org.jusecase.bitpack.buffer.BufferBitWriter;

import java.nio.ByteBuffer;

import static org.assertj.core.api.Assertions.assertThat;

class AttackCreepCommandSerializerTest {
    BitProtocol protocol = new AbstractBitProtocol();
    BufferBitWriter writer = new BufferBitWriter(protocol, ByteBuffer.allocateDirect(256));
    BufferBitReader reader;

    @BeforeEach
    void setUp() {
        protocol.register(new AttackCreepCommandSerializer());
    }

    @Test
    void creepIds() {
        AttackCreepCommand expected = new AttackCreepCommand();
        expected.creepId = Creep.MAX_CREEP_ID;
        writer.writeObjectNonNull(expected);

        whenBufferIsFlushedAndRead();

        AttackCreepCommand actual = reader.readObjectNonNull(AttackCreepCommand.class);
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    private void whenBufferIsFlushedAndRead() {
        writer.flush();
        writer.getBuffer().rewind();
        reader = new BufferBitReader(protocol, writer.getBuffer());
    }
}