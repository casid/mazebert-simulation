package com.mazebert.simulation.gateways;

import com.mazebert.simulation.messages.Turn;

import java.util.List;

public strictfp interface TurnGateway {

    int getCurrentTurnNumber();

    void setCurrentTurnNumber(int currentTurnNumber);

    List<Turn> waitForAllPlayerTurns(MessageGateway messageGateway);

    void incrementTurnNumber();

    int clampTurnNumber(long turnNumber);

    void onLocalTurnReceived(Turn turn);

    @SuppressWarnings("unused")
    void onRemoteTurnReceived(Turn turn);

    boolean isMissing(Turn turn, int turnNumber);

    Turn getTurn(int turnNumber, int playerId);

    int getTurnNumberForLocalCommands();

    void setRunning(boolean running);
}
