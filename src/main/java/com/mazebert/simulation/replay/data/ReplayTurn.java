package com.mazebert.simulation.replay.data;

import com.mazebert.simulation.commands.Command;
import com.mazebert.simulation.messages.Turn;

import java.util.Collections;
import java.util.List;

public strictfp class ReplayTurn {
    public List<Command> commands = Collections.emptyList();

    public static ReplayTurn fromTurn(Turn turn) {
        ReplayTurn replayTurn = new ReplayTurn();
        replayTurn.commands = turn.commands;
        return replayTurn;
    }

    public static Turn toTurn(ReplayTurn replayTurn, int playerId, int turnNumber, int hash) {
        Turn turn = new Turn();
        turn.turnNumber = turnNumber;
        turn.playerId = playerId;
        turn.hash = hash;
        turn.commands = replayTurn.commands;
        return turn;
    }
}
