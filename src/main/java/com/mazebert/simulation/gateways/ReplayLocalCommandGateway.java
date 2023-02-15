package com.mazebert.simulation.gateways;

import com.mazebert.simulation.commands.Command;

import java.util.Collections;
import java.util.List;

@SuppressWarnings("unused") // Used by replay
public strictfp class ReplayLocalCommandGateway implements LocalCommandGateway {

    @Override
    public void addCommand(Command command) {
        // no commands need to be added in a replay
    }

    @Override
    public List<Command> reset() {
        return Collections.emptyList();
    }
}
