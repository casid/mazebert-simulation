package com.mazebert.simulation.replay;

import com.mazebert.simulation.replay.data.ReplayFrame;
import com.mazebert.simulation.replay.data.ReplayHeader;
import org.jusecase.bitpack.stream.EndOfStreamException;
import org.jusecase.bitpack.stream.StreamBitReader;

import java.io.IOException;
import java.io.InputStream;

public strictfp class StreamReplayReader implements AutoCloseable, ReplayReader {
    private final StreamBitReader reader;
    private final boolean silenceExceptions;

    public StreamReplayReader(InputStream inputStream, int version) {
        this(inputStream, version, false);
    }

    public StreamReplayReader(InputStream inputStream, int version, boolean silenceExceptions) {
        reader = new StreamBitReader(new ReplayProtocol(version), inputStream);
        this.silenceExceptions = silenceExceptions;
    }

    @Override
    public ReplayHeader readHeader() {
        return reader.readObjectNonNull(ReplayHeader.class);
    }

    @Override
    public ReplayFrame readFrame() {
        if (silenceExceptions) {
            try {
                return reader.readObjectNullable(ReplayFrame.class);
            } catch (Exception e) {
                return null;
            }
        } else {
            try {
                return reader.readObjectNullable(ReplayFrame.class);
            } catch (EndOfStreamException e) {
                return null;
            }
        }
    }

    @Override
    public void close() {
        try {
            reader.close();
        } catch (IOException e) {
            // Silently
        }
    }
}
