package com.mazebert.simulation.plugins;

public class NoSleepPlugin extends SleepPlugin {
    @Override
    public void sleep(long nanos) {
        // do nothing
    }
}
