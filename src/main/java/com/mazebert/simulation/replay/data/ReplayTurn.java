package com.mazebert.simulation.replay.data;

import com.mazebert.simulation.commands.Command;
import com.mazebert.simulation.messages.Turn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public strictfp class ReplayTurn {
    public List<Command> commands = Collections.emptyList();

    public static ReplayTurn fromTurn(Turn turn) {
        ReplayTurn replayTurn = new ReplayTurn();
        replayTurn.commands = extractCommands(turn.commands);
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

    private static List<Command> extractCommands(List<Command> commands) {
        if (areAllCommandsRequired(commands)) {
            return commands;
        }

        List<Command> result = new ArrayList<>();
        for (Command command : commands) {
            if (!command.excludedFromReplay) {
                result.add(command);
            }
        }
        return result;
    }

    private static boolean areAllCommandsRequired(List<Command> commands) {
        for (Command command : commands) {
            if (command.excludedFromReplay) {
                return false;
            }
        }
        return true;
    }

}
