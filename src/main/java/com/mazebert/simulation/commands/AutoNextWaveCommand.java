package com.mazebert.simulation.commands;

public strictfp class AutoNextWaveCommand extends Command {
    public boolean autoNextWave;

    @Override
    public boolean isValid() {
        return true;
    }
}
