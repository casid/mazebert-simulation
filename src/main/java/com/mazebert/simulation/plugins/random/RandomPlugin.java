package com.mazebert.simulation.plugins.random;

import java.util.UUID;

public strictfp abstract class RandomPlugin {
    /**
     * floating point number generation between [-1.0,1.0[
     */
    public abstract float getFloat();

    /**
     * floating point number generation between [0.0,1.0[
     */
    public abstract float getFloatAbs();

    public abstract UUID getUuid();

    /**
     * floating point number between [min,max[
     */
    public float getFloat(float min, float max) {
        return min + (max - min) * getFloatAbs();
    }

    /**
     * floating point number between [min,max[
     */
    public double getDouble(double min, double max) {
        return min + (max - min) * getFloatAbs();
    }

    /**
     * integer number between [min,max]
     */
    public int getInt(int min, int max) {
        return (int) (min + (max - min + 1) * getFloatAbs());
    }

    /**
     * integer number between [min,max]
     */
    public long getLong(long min, long max) {
        return (long) (min + (max - min + 1) * getFloatAbs());
    }

    public <T> T get(T[] array) {
        return array[getInt(0, array.length - 1)];
    }
}
