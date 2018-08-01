package com.mazebert.simulation.plugins;

public strictfp class SleepPlugin {
    public void sleep(long nanos) {
        long millis = nanos / 1000000L;
        long nanosRemainder = nanos % 1000000L;

        try {
            Thread.sleep(millis, (int)nanosRemainder);
        } catch (InterruptedException e) {
            // Ignore
        }
    }
}
