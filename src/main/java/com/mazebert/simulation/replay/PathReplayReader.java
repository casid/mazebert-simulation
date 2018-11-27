package com.mazebert.simulation.replay;

import com.mazebert.simulation.replay.data.ReplayFrame;
import com.mazebert.simulation.replay.data.ReplayHeader;
import org.jusecase.bitpack.stream.StreamBitReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public strictfp class PathReplayReader implements AutoCloseable, ReplayReader {
    private final StreamBitReader reader;

    public PathReplayReader(Path path) {
        try {
            reader = new StreamBitReader(new ReplayProtocol(), Files.newInputStream(path, StandardOpenOption.READ));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ReplayHeader readHeader() {
        return reader.readObjectNonNull(ReplayHeader.class);
    }

    @Override
    public ReplayFrame readFrame() {
        return reader.readObjectNonNull(ReplayFrame.class);
    }

    public void close() throws IOException {
        reader.close();
    }
}
