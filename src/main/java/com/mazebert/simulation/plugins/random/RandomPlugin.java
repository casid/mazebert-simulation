package com.mazebert.simulation.plugins.random;

public interface RandomPlugin {
    void setSeed(long seed);

    int nextInt();
}
