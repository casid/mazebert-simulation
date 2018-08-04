package com.mazebert.simulation.replay;

import com.mazebert.simulation.replay.data.ReplayHeader;
import com.mazebert.simulation.replay.data.ReplayTurn;
import org.jusecase.bitpack.BitReader;
import org.jusecase.bitpack.stream.StreamBitReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public strictfp class ReplayReader implements AutoCloseable {
    private final StreamBitReader reader;

    public ReplayReader(Path path) {
        try {
            reader = new StreamBitReader(new ReplayProtocol(), Files.newInputStream(path, StandardOpenOption.READ));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ReplayHeader readHeader() {
        return reader.readObjectNonNull(ReplayHeader.class);
    }

    public ReplayTurn readTurn() {
        return reader.readObjectNonNull(ReplayTurn.class);
    }

    public void close() throws IOException {
        reader.close();
    }
}
