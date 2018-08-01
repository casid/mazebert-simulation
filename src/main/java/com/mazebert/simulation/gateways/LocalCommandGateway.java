package com.mazebert.simulation.gateways;

import com.mazebert.simulation.commands.Command;

import java.util.ArrayList;
import java.util.List;

public strictfp class LocalCommandGateway {

    private final List<Command> localPlayerCommands = new ArrayList<>();

    public void addCommand(Command command) {
        synchronized (localPlayerCommands) {
            localPlayerCommands.add(command);
        }
    }

    public List<Command> reset() {
        synchronized (localPlayerCommands) {
            ArrayList<Command> commands = new ArrayList<>(localPlayerCommands);
            localPlayerCommands.clear();
            return commands;
        }
    }
}
