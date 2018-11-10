package com.mazebert.simulation.replay;

import com.mazebert.simulation.gateways.ReplayWriterGateway;
import com.mazebert.simulation.messages.Turn;
import com.mazebert.simulation.replay.data.ReplayHeader;
import com.mazebert.simulation.replay.data.ReplayTurn;
import org.jusecase.bitpack.stream.StreamBitWriter;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.nio.file.StandardOpenOption.*;

public strictfp class ReplayWriter implements ReplayWriterGateway, AutoCloseable {
    private final StreamBitWriter writer;
    private final ReplayTurn replayTurn = new ReplayTurn();

    public ReplayWriter(OutputStream outputStream) {
        writer = new StreamBitWriter(new ReplayProtocol(), outputStream);
    }

    @Override
    public boolean isWriteEnabled() {
        return true;
    }

    @Override
    public void writeHeader(ReplayHeader header) {
        write(header);
    }

    @Override
    public void writeTurn(List<Turn> playerTurns) {
        replayTurn.playerTurns = playerTurns;
        writeTurn(replayTurn);
    }

    public void writeTurn(ReplayTurn turn) {
        write(turn);
    }

    public void close() {
        try {
            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void write(Object object) {
        writer.writeObjectNonNull(object);
    }
}
