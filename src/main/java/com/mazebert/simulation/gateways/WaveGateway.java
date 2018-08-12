package com.mazebert.simulation.gateways;

import com.mazebert.simulation.Wave;

import java.util.ArrayDeque;
import java.util.Queue;

public strictfp class WaveGateway {
    private Queue<Wave> waves = new ArrayDeque<>();

    public void addWave(Wave wave) {
        waves.add(wave);
    }

    public Wave getNextWave() {
        return waves.peek();
    }
}
