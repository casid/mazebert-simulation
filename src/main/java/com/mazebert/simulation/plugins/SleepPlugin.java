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

    public long nanoTime() {
        return System.nanoTime();
    }

    public void sleepUntil(long start, long nanos) {
        long duration = nanoTime() - start;
        duration = nanos - duration;

        if (duration > 0) {
            sleep(duration);
        }
    }
}
