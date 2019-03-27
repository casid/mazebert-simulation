package com.mazebert.simulation.gateways;

import com.mazebert.simulation.messages.Turn;

import java.util.List;

public strictfp interface TurnGateway {

    int getCurrentTurnNumber();

    List<Turn> waitForAllPlayerTurns(MessageGateway messageGateway);

    void incrementTurnNumber();

    void onLocalTurnReceived(Turn turn);

    @SuppressWarnings("unused") // Called by client
    void onRemoteTurnReceived(Turn turn);

    int getTurnNumberForLocalCommands();

    void setRunning(boolean running);

    static int clampTurnNumber(long turnNumber) {
        while (turnNumber >= Integer.MAX_VALUE) {
            turnNumber -= Integer.MAX_VALUE;
        }
        return (int) turnNumber;
    }
}
