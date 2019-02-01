package com.mazebert.simulation.gateways;

import com.mazebert.simulation.messages.ResendTurn;
import com.mazebert.simulation.messages.Turn;

import java.util.Arrays;
import java.util.List;

public strictfp class TurnGateway {

    private final int maxPlayerCount;
    private final int turnBufferSize = 64;
    private final Turn[][] turns;
    private final Object turnWaiter = new Object();

    private int currentTurnNumber;
    private boolean running = true;

    public TurnGateway(int maxPlayerCount) {
        this.maxPlayerCount = maxPlayerCount;
        turns = new Turn[turnBufferSize][maxPlayerCount];
    }

    public int getCurrentTurnNumber() {
        return currentTurnNumber;
    }

    public void setCurrentTurnNumber(int currentTurnNumber) {
        this.currentTurnNumber = clampTurnNumber(currentTurnNumber);
    }

    public List<Turn> waitForAllPlayerTurns(MessageGateway messageGateway) {
        boolean firstAttempt = true;

        // Wait until all player turns have arrived
        while (running && !isCurrentTurnAvailableForEachPlayer()) {
            if (!firstAttempt) {
                askForMissingTurns(messageGateway);
            } else {
                firstAttempt = false;
            }
            synchronized (turnWaiter) {
                try {
                    turnWaiter.wait(50);
                } catch (InterruptedException e) {
                    // Ignore, loop will retry
                }
            }
        }

        Turn[] turns = getCurrentPlayerTurns();
        return Arrays.asList(turns);
    }

    public void incrementTurnNumber() {
        currentTurnNumber = clampTurnNumber(currentTurnNumber + 1L);
    }

    public int clampTurnNumber(long turnNumber) {
        while (turnNumber >= Integer.MAX_VALUE) {
            turnNumber -= Integer.MAX_VALUE;
        }
        return (int) turnNumber;
    }

    private void askForMissingTurns(MessageGateway messageGateway) {
        Turn[] currentPlayerTurns = getCurrentPlayerTurns();
        for (int playerIndex = 0; playerIndex < currentPlayerTurns.length; ++playerIndex) {
            Turn playerTurn = currentPlayerTurns[playerIndex];
            if (isMissing(playerTurn, currentTurnNumber)) {
                ResendTurn message = new ResendTurn();
                message.turnNumber = currentTurnNumber;
                messageGateway.sendToPlayer(playerIndex + 1, message);
            }
        }
    }

    public void onLocalTurnReceived(Turn turn) {
        addTurn(turn);
    }

    @SuppressWarnings("unused")
    public void onRemoteTurnReceived(Turn turn) {
        if (addTurn(turn)) {
            synchronized (turnWaiter) {
                turnWaiter.notify();
            }
        }
    }

    private boolean addTurn(Turn turn) {
        if (turn.playerId > 0 && turn.playerId <= maxPlayerCount) {
            int playerIndex = turn.playerId - 1;
            int turnIndex = turn.turnNumber % turnBufferSize;

            if (isMissing(turns[turnIndex][playerIndex], turn.turnNumber)) {
                turns[turnIndex][playerIndex] = turn;
                return true;
            }
        }
        return false;
    }

    private int getCurrentTurnIndex() {
        return currentTurnNumber % turnBufferSize;
    }

    private boolean isCurrentTurnAvailableForEachPlayer() {
        Turn[] turns = getCurrentPlayerTurns();

        for (Turn turn : turns) {
            if (isMissing(turn, currentTurnNumber)) {
                return false;
            }
        }

        return true;
    }

    private Turn[] getCurrentPlayerTurns() {
        return this.turns[getCurrentTurnIndex()];
    }

    public boolean isMissing(Turn turn, int turnNumber) {
        return turn == null || turn.turnNumber < turnNumber;
    }

    public Turn getTurn(int turnNumber, int playerId) {
        int playerIndex = playerId - 1;
        int turnIndex = turnNumber % turnBufferSize;

        return turns[turnIndex][playerIndex];
    }

    public int getTurnNumberForLocalCommands() {
        return clampTurnNumber(currentTurnNumber + 2L);
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
