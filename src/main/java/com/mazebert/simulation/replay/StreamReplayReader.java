package com.mazebert.simulation.replay;

import com.mazebert.simulation.replay.data.ReplayFrame;
import com.mazebert.simulation.replay.data.ReplayHeader;
import org.jusecase.bitpack.stream.StreamBitReader;

import java.io.IOException;
import java.io.InputStream;

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
            return reader.readObjectNullable(ReplayFrame.class);
        } catch (Exception e) {
            e.printStackTrace(); // TODO log
            return null;
        }
    }

    @Override
    public void close() {
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace(); // TODO log
        }
    }
}
