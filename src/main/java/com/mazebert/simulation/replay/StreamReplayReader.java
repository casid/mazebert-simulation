package com.mazebert.simulation.replay;

import com.mazebert.simulation.replay.data.ReplayFrame;
import com.mazebert.simulation.replay.data.ReplayHeader;
import org.jusecase.bitpack.stream.StreamBitReader;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public strictfp class StreamReplayReader implements AutoCloseable, ReplayReader {
    private final StreamBitReader reader;

    public StreamReplayReader(InputStream inputStream) {
        reader = new StreamBitReader(new ReplayProtocol(), inputStream);
    }

    @Override
    public ReplayHeader readHeader() {
        return reader.readObjectNonNull(ReplayHeader.class);
    }

    @Override
    public ReplayFrame readFrame() {
        try {
            return reader.readObjectNonNull(ReplayFrame.class);
        } catch (RuntimeException e) {
            return null;
        }
    }

    public void close() throws IOException {
        reader.close();
    }
}
