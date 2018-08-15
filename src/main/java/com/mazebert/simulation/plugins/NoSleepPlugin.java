package com.mazebert.simulation.plugins;

public strictfp class NoSleepPlugin extends SleepPlugin {
    @Override
    public void sleep(long nanos) {
        // do nothing
    }

    @Override
    public long nanoTime() {
        return 0L; // do nothing
    }

    @Override
    public void sleepUntil(long start, long nanos) {
        // do nothing
    }
}
