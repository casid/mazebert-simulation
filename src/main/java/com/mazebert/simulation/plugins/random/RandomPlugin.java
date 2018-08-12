package com.mazebert.simulation.plugins.random;

public strictfp interface RandomPlugin {
    void setSeed(int seed);

    /**
     * floating point number generation between [-1.0,1.0[
     */
    float getFloat();

    /**
     * floating point number generation between [0.0,1.0[
     */
    float getFloatAbs();

    /**
     * floating point number between [min,max[
     */
    default float getFloat(float min, float max) {
        return min + (max - min) * getFloatAbs();
    }

    /**
     * floating point number between [min,max[
     */
    default double getDouble(double min, double max) {
        return min + (max - min) * getFloatAbs();
    }

    /**
     * integer number between [min,max]
     */
    default int getInt(int min, int max) {
        return (int) (min + (max - min + 1) * getFloatAbs());
    }

    /**
     * integer number between [min,max]
     */
    default long getLong(long min, long max) {
        return (long) (min + (max - min + 1) * getFloatAbs());
    }

    default <T> T get(T[] array) {
        return array[getInt(0, array.length - 1)];
    }
}
