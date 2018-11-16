package com.mazebert.simulation.gateways;

import com.mazebert.simulation.Wave;

import java.util.Collection;

public interface ReadonlyWaveGateway {
    Collection<Wave> getWaves();

    int getTotalWaves();

    int getCurrentRound();

    Wave getCurrentWave();
}
