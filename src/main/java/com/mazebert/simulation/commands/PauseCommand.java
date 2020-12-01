package com.mazebert.simulation.commands;

public strictfp class PauseCommand extends Command {
    public boolean pause;

    @Override
    public boolean isValid() {
        return true;
    }
}
