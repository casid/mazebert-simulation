package com.mazebert.simulation.gateways;

import com.mazebert.simulation.commands.Command;
import com.mazebert.simulation.messages.serializers.TurnSerializer;

import java.util.ArrayList;
import java.util.List;

public strictfp class GameLocalCommandGateway implements LocalCommandGateway {

    private final List<Command> localPlayerCommands = new ArrayList<>();

    @Override
    public void addCommand(Command command) {
        synchronized (localPlayerCommands) {
            if (localPlayerCommands.size() < TurnSerializer.MAX_COMMANDS) {
                localPlayerCommands.add(command);
            }
        }
    }

    @Override
    public List<Command> reset() {
        synchronized (localPlayerCommands) {
            ArrayList<Command> commands = new ArrayList<>(localPlayerCommands);
            localPlayerCommands.clear();
            return commands;
        }
    }
}
