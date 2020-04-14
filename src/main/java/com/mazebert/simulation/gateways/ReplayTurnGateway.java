package com.mazebert.simulation.gateways;

import com.mazebert.simulation.messages.Turn;
import com.mazebert.simulation.replay.ReplayReader;
import com.mazebert.simulation.replay.data.ReplayFrame;

import java.util.Collections;
import java.util.List;

@SuppressWarnings("unused") // By client
public strictfp class ReplayTurnGateway implements TurnGateway {
    private final ReplayReader replayReader;

    private ReplayFrame nextFrame;
    private int currentTurnNumber;

    public ReplayTurnGateway(ReplayReader replayReader) {
        this.replayReader = replayReader;
        nextFrame = replayReader.readFrame();
    }

    @Override
    public int getCurrentTurnNumber() {
        return currentTurnNumber;
    }

    @Override
    public List<Turn> waitForAllPlayerTurns(MessageGateway messageGateway) {
        if (nextFrame == null) {
            return Collections.emptyList();
        }

        if (currentTurnNumber < nextFrame.turnNumber) {
            return Collections.emptyList();
        } else {
            List<Turn> turns = nextFrame.getTurns();
            nextFrame = replayReader.readFrame();
            return turns;
        }
    }

    @Override
    public void incrementTurnNumber() {
        currentTurnNumber = TurnGateway.clampTurnNumber(currentTurnNumber + 1L);
    }

    @Override
    public void onLocalTurnReceived(Turn turn) {
        // Ignored, we already have all turns
    }

    @Override
    public void onRemoteTurnReceived(Turn turn) {
        // Ignored, we already have all turns
    }

    @Override
    public int getTurnNumberForLocalCommands() {
        return 0; // Not relevant
    }

    @Override
    public void setRunning(boolean running) {
        // not used
    }
}
