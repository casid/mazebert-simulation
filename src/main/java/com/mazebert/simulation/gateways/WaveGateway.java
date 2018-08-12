package com.mazebert.simulation.gateways;

import com.mazebert.simulation.Wave;
import com.mazebert.simulation.WaveType;
import com.mazebert.simulation.plugins.random.RandomPlugin;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Queue;

public strictfp class WaveGateway {
    public static final int WAVES_IN_ADVANCE = 5;
    private static final WaveType[] RANDOM_WAVE_TYPES = {WaveType.Normal, WaveType.Mass, WaveType.Boss, WaveType.Air};

    private Queue<Wave> waves = new ArrayDeque<>(WAVES_IN_ADVANCE);
    private int totalWaves;
    private int currentWave;

    public void addWave(Wave wave) {
        waves.add(wave);
    }

    public Wave getNextWave() {
        Wave wave = waves.poll();
        if (wave != null) {
            ++currentWave;
        }
        return wave;
    }

    public Collection<Wave> getWaves() {
        return waves;
    }

    public void generateMissingWaves(RandomPlugin randomPlugin) {
        int missingWaves = Math.min(totalWaves - currentWave, WaveGateway.WAVES_IN_ADVANCE);
        for (int i = 0; i < missingWaves; ++i) {
            Wave wave = new Wave();
            wave.type = randomPlugin.get(RANDOM_WAVE_TYPES);
            wave.creepCount = wave.type.creepCount;
            wave.minSecondsToNextCreep = 1.0f;
            wave.maxSecondsToNextCreep = 1.6f;

            addWave(wave);
        }
    }

    public void setTotalWaves(int totalWaves) {
        this.totalWaves = totalWaves;
    }

    public int getCurrentWave() {
        return currentWave;
    }
}
