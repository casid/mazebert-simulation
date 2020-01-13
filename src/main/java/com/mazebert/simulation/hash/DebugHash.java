package com.mazebert.simulation.hash;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.plugins.LogPlugin;

import java.util.UUID;

public strictfp class DebugHash extends Hash {
    private final LogPlugin logPlugin = Sim.context().logPlugin;

    public void add(boolean value) {
        super.add(value);
        log("boolean=" + value);
    }

    public void add(int value) {
        super.add(value);
        log("int=" + value);
    }

    public void add(long value) {
        super.add(value);
        log("long=" + value);
    }

    public void add(float value) {
        super.add(value);
        log("float=" + value + ", bin=" + Float.floatToIntBits(value));
    }

    public void add(double value) {
        super.add(value);
        log("double=" + value + ", bin=" + Double.doubleToLongBits(value));
    }

    @Override
    public void add(Enum<?> value) {
        super.add(value);
        log("enum=" + value);
    }

    @Override
    public void add(UUID value) {
        super.add(value);
        log("uuid=" + value);
    }

    @Override
    public void add(Hashable value) {
        log("Begin hash of " + value);
        super.add(value);
        log("End hash of " + value);
    }

    private void log(String message) {
        logPlugin.log(get() + ": " + message);
    }
}
