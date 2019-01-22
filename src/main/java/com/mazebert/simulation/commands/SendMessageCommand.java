package com.mazebert.simulation.commands;

public class SendMessageCommand extends Command {
    public SendMessageCommand() {
        excludedFromReplay = true;
    }

    public String message;
}
