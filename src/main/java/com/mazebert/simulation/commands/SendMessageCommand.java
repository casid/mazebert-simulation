package com.mazebert.simulation.commands;

public strictfp class SendMessageCommand extends Command {
    public SendMessageCommand() {
        excludedFromReplay = true;
    }

    public String message;

    @Override
    public boolean isValid() {
        return true;
    }
}
