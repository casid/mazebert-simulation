package com.mazebert.simulation;

import com.mazebert.simulation.commands.Command;

public class CommandExecutorTrainer extends CommandExecutor {
    private Command command;

    @Override
    public void init() {
        // do nothing
    }

    @Override
    public <C extends Command> void execute(C command) {
        this.command = command;
    }

    public Command getLastCommand() {
        return command;
    }
}