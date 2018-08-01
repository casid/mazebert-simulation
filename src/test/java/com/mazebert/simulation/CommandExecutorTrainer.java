package com.mazebert.simulation;

import com.mazebert.simulation.commands.Command;

public class CommandExecutorTrainer extends CommandExecutor {
    private Command command;

    @Override
    public void init() {
        // do nothing
    }

    @Override
    public <Request> void executeVoid(Request request) {
        command = (Command)request;
    }

    public Command getLastCommand() {
        return command;
    }
}