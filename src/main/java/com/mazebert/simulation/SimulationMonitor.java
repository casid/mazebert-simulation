package com.mazebert.simulation;

public strictfp final class SimulationMonitor {
    private final boolean synchronize;

    public SimulationMonitor() {
        this(false);
    }

    public SimulationMonitor(boolean synchronize) {
        this.synchronize = synchronize;
    }

    public boolean isRequired() {
        return synchronize;
    }

    public Object get() {
        return this;
    }
}
