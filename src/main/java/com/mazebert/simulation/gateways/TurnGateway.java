package com.mazebert.simulation.gateways;

import com.mazebert.simulation.messages.ResendTurn;
import com.mazebert.simulation.messages.Turn;
import org.jusecase.inject.Component;

import java.util.Arrays;
import java.util.List;

@Component
public strictfp class TurnGateway {

    private final int maxPlayerCount;
    private final int turnBufferSize = 64;
    private final Turn[][] turns;
    private final Object turnWaiter = new Object();

    private int currentTurnNumber;

    public TurnGateway(int maxPlayerCount) {
        this.maxPlayerCount = maxPlayerCount;
        turns = new Turn[turnBufferSize][maxPlayerCount];
    }

    public int getCurrentTurnNumber() {
        return currentTurnNumber;
    }

    public List<Turn> waitForAllPlayerTurns(MessageGateway messageGateway) {
        boolean firstAttempt = true;

        // Wait until all player turns have arrived
        while (!isCurrentTurnAvailableForEachPlayer()) {
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
        for (Turn turn : turns) {
            turn.processed = true;
        }

        return Arrays.asList(turns);
    }

    public void incrementTurnNumber() {
        currentTurnNumber = clampTurnNumber(currentTurnNumber + 1);
    }

    private int clampTurnNumber(int turnNumber) {
        while (turnNumber >= turnBufferSize) {
            turnNumber -= turnBufferSize;
        }
        return turnNumber;
    }

    private void askForMissingTurns(MessageGateway messageGateway) {
        Turn[] currentPlayerTurns = getCurrentPlayerTurns();
        for (int playerIndex = 0; playerIndex < currentPlayerTurns.length; ++playerIndex ) {
            Turn playerTurn = currentPlayerTurns[playerIndex];
            if (isMissing(playerTurn)) {
                ResendTurn message = new ResendTurn();
                message.turnNumber = currentTurnNumber;
                messageGateway.sendToPlayer(playerIndex + 1, message);
            }
        }
    }

    public void onLocalTurnReceived(Turn turn) {
        addTurn(turn);
    }

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

            if (turns[turn.turnNumber][playerIndex] == null || turns[turn.turnNumber][playerIndex].processed) {
                turns[turn.turnNumber][playerIndex] = turn;
                return true;
            }
        }
        return false;
    }

    private boolean isCurrentTurnAvailableForEachPlayer() {
        Turn[] turns = getCurrentPlayerTurns();

        for (Turn turn : turns) {
            if (isMissing(turn)) {
                return false;
            }
        }

        return true;
    }

    private Turn[] getCurrentPlayerTurns() {
        return this.turns[currentTurnNumber];
    }

    private boolean isMissing(Turn turn) {
        return turn == null || turn.processed;
    }

    public Turn getTurn(int turnNumber, int playerId) {
        int playerIndex = playerId - 1;

        return turns[turnNumber][playerIndex];
    }

    public int getTurnNumberForLocalCommands() {
        return clampTurnNumber(currentTurnNumber + 2);
    }
}
