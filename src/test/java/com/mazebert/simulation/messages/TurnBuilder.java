package com.mazebert.simulation.messages;

import com.mazebert.simulation.commands.Command;
import org.jusecase.builders.Builder;

import java.util.Collections;

public class TurnBuilder implements Builder<Turn> {

    private final Turn turn;

    public TurnBuilder() {
        turn = new Turn();
        turn.playerId = 1;
        turn.turnNumber = 0;
    }

    public static TurnBuilder turn() {
        return new TurnBuilder();
    }

    public TurnBuilder withCommand(Command command) {
        turn.commands = Collections.singletonList(command);
        return this;
    }

    public TurnBuilder withTurnNumber(int value) {
        turn.turnNumber = value;
        return this;
    }

    @Override
    public Turn build() {
        return turn;
    }
}