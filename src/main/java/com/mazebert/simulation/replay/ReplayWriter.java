package com.mazebert.simulation.replay;

import com.mazebert.simulation.gateways.ReplayWriterGateway;
import com.mazebert.simulation.messages.Turn;
import com.mazebert.simulation.replay.data.ReplayFrame;
import com.mazebert.simulation.replay.data.ReplayHeader;
import com.mazebert.simulation.replay.data.ReplayTurn;
import org.jusecase.bitpack.stream.StreamBitWriter;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public strictfp class ReplayWriter implements ReplayWriterGateway, AutoCloseable {
    private final StreamBitWriter writer;
    private final ReplayFrame replayFrame = new ReplayFrame();

    public ReplayWriter(OutputStream outputStream) {
        writer = new StreamBitWriter(new ReplayProtocol(), outputStream);
        replayFrame.playerTurns = new ReplayTurn[1];
    }

    @Override
    public boolean isWriteEnabled() {
        return true;
    }

    @Override
    public void writeHeader(ReplayHeader header) {
        writer.writeObjectNonNull(header);
    }

    @Override
    public void writeTurn(int currentTurnNumber, List<Turn> playerTurns, int myHash) {
        if (isRequiredToWriteTurn(playerTurns)) {
            replayFrame.turnNumber = currentTurnNumber;
            replayFrame.hash = myHash;

            if (replayFrame.playerTurns.length != playerTurns.size()) {
                replayFrame.playerTurns = new ReplayTurn[playerTurns.size()];
            }

            for (int i = 0; i < replayFrame.playerTurns.length; ++i) {
                replayFrame.playerTurns[i] = ReplayTurn.fromTurn(playerTurns.get(i));
            }

            writeFrame(replayFrame);
        }
    }

    @Override
    public void writeEnd(int currentTurnNumber, int myHash) {
        replayFrame.turnNumber = currentTurnNumber;
        replayFrame.hash = myHash;
        replayFrame.playerTurns = null;
        writeFrame(replayFrame);
    }

    @Override
    public void writePreviousReplay(ReplayReader reader) {
        writeHeader(reader.readHeader());
        while (true) {
            ReplayFrame frame = reader.readFrame();
            if (frame != null) {
                writeFrame(frame);
            } else {
                break;
            }
        }
    }

    public void writeFrame(ReplayFrame turn) {
        writer.writeObjectNullable(turn);
    }

    @Override
    public void close() {
        try {
            writeFrame(null); // mark end of stream
            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isRequiredToWriteTurn(List<Turn> playerTurns) {
        for (Turn playerTurn : playerTurns) {
            if (!playerTurn.commands.isEmpty()) {
                return true;
            }
        }
        return false;
    }

}
