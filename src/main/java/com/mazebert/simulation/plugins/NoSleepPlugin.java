package com.mazebert.simulation.plugins;

public strictfp class NoSleepPlugin extends SleepPlugin {
    @Override
    public void sleep(long nanos) {
        // do nothing
    }
}
