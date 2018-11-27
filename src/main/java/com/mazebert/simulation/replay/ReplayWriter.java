package com.mazebert.simulation.replay;

import com.mazebert.simulation.gateways.ReplayWriterGateway;
import com.mazebert.simulation.messages.Turn;
import com.mazebert.simulation.replay.data.ReplayHeader;
import com.mazebert.simulation.replay.data.ReplayTurn;
import com.mazebert.simulation.replay.data.ReplayFrame;
import org.jusecase.bitpack.stream.StreamBitWriter;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public strictfp class ReplayWriter implements ReplayWriterGateway, AutoCloseable {
    private final StreamBitWriter writer;
    private final ReplayFrame replayFrame = new ReplayFrame();

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
    public void writeTurn(int currentTurnNumber, List<Turn> playerTurns) {
        if (isRequiredToWriteTurn(playerTurns)) {
            replayFrame.turnNumber = currentTurnNumber;

            if (replayFrame.playerTurns.length != playerTurns.size()) {
                replayFrame.playerTurns = new ReplayTurn[playerTurns.size()];
            }

            for (int i = 0; i < replayFrame.playerTurns.length; ++i) {
                replayFrame.playerTurns[i] = ReplayTurn.fromTurn(playerTurns.get(i));
            }

            writeTurn(replayFrame);
        }
    }

    public void writeTurn(ReplayFrame turn) {
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

    private boolean isRequiredToWriteTurn(List<Turn> playerTurns) {
        for (Turn playerTurn : playerTurns) {
            if (!playerTurn.commands.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private void write(Object object) {
        writer.writeObjectNonNull(object);
    }
}
