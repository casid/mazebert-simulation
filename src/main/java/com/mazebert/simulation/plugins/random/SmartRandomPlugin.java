package com.mazebert.simulation.plugins.random;

import com.mazebert.simulation.SimulationLoop;

public strictfp class SmartRandomPlugin implements RandomPlugin {
    private final JdkRandomPlugin synced = new JdkRandomPlugin();
    private final JdkRandomPlugin unsynced = new JdkRandomPlugin();

    @Override
    public void setSeed(long seed) {
        resolvePlugin().setSeed(seed);
    }

    @Override
    public int nextInt() {
        return resolvePlugin().nextInt();
    }

    private RandomPlugin resolvePlugin() {
        if (SimulationLoop.THREAD_NAME.equals(Thread.currentThread().getName())) {
            return synced;
        }
        return unsynced;
    }
}
