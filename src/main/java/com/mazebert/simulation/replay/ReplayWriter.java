package com.mazebert.simulation.replay;

import com.mazebert.simulation.replay.data.ReplayHeader;
import com.mazebert.simulation.replay.data.ReplayTurn;
import org.jusecase.bitpack.buffer.BufferBitWriter;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public strictfp class ReplayWriter {
    private final BufferBitWriter writer;
    private final ByteChannel outputChannel;

    public ReplayWriter(Path path) {
        writer = new BufferBitWriter(new ReplayProtocol(), ByteBuffer.allocateDirect(ReplayProtocol.MAX_BYTES_PER_ENTITY));
        try {
            outputChannel = Files.newByteChannel(path, StandardOpenOption.WRITE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeHeader(ReplayHeader header) {
        write(header);
    }

    public void writeTurn(ReplayTurn turn) {
        write(turn);
    }

    public void close() {
        try {
            outputChannel.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void write(Object object) {
        writer.writeObjectNonNull(object);
        writer.flush();

        try {
            ByteBuffer buffer = writer.getBuffer();
            buffer.limit(buffer.position());
            buffer.position(0);
            outputChannel.write(buffer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        writer.reset();
    }
}
