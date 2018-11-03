package com.mazebert.simulation.plugins.random;

import com.mazebert.simulation.SimulationLoop;

import java.util.UUID;

public strictfp class SmartRandomPlugin implements RandomPlugin {
    private final FastRandomPlugin synced = new FastRandomPlugin();
    private final FastRandomPlugin unsynced = new FastRandomPlugin();

    @Override
    public void setSeed(int seed) {
        resolvePlugin().setSeed(seed);
    }

    @Override
    public float getFloat() {
        return resolvePlugin().getFloat();
    }

    @Override
    public float getFloatAbs() {
        return resolvePlugin().getFloatAbs();
    }

    @Override
    public UUID getUuid() {
        return resolvePlugin().getUuid();
    }

    @Override
    public float getFloat(float min, float max) {
        return resolvePlugin().getFloat(min, max);
    }

    @Override
    public int getInt(int min, int max) {
        return resolvePlugin().getInt(min, max);
    }

    private RandomPlugin resolvePlugin() {
        if (SimulationLoop.THREAD_NAME.equals(Thread.currentThread().getName())) {
            return synced;
        }
        return unsynced;
    }
}
