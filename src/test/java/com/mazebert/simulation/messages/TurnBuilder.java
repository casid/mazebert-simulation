package com.mazebert.simulation.messages;

import com.mazebert.simulation.commands.Command;

import java.util.Collections;

public class TurnBuilder implements TurnBuilderMethods<Turn, TurnBuilder> {

    private Turn turn;

    public TurnBuilder() {
        turn = new Turn();
        turn.playerId = 1;
        turn.turnNumber = 0;
    }

    public static TurnBuilder turn() {
        return new TurnBuilder();
    }

    @Override
    public Turn getEntity() {
        return turn;
    }

    public TurnBuilder withCommand(Command command) {
        turn.commands = Collections.singletonList(command);
        return this;
    }
}