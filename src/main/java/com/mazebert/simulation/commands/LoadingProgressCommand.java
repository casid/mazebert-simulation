package com.mazebert.simulation.commands;

public class LoadingProgressCommand extends Command {
    public LoadingProgressCommand() {
        excludedFromReplay = true;
    }

    public int progress;
}
