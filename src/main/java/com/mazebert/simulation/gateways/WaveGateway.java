package com.mazebert.simulation.gateways;

import com.mazebert.simulation.Wave;
import com.mazebert.simulation.WaveType;
import com.mazebert.simulation.plugins.random.RandomPlugin;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Queue;

public strictfp class WaveGateway {
    public static final int WAVES_IN_ADVANCE = 3;
    private static final WaveType[] RANDOM_WAVE_TYPES = {WaveType.Normal, WaveType.Mass, WaveType.Boss, WaveType.Air};

    private Queue<Wave> waves = new ArrayDeque<>(WAVES_IN_ADVANCE);
    private int totalWaves;
    private int generatedWaves;
    private int currentRound;
    private Wave currentWave;

    public void addWave(Wave wave) {
        waves.add(wave);
    }

    public Wave nextWave() {
        Wave wave = waves.poll();
        if (wave != null) {
            currentWave = wave;
            ++currentRound;
        }
        return wave;
    }

    public Collection<Wave> getWaves() {
        return waves;
    }

    public void generateMissingWaves(RandomPlugin randomPlugin) {
        int missingWaves = StrictMath.min(totalWaves - currentRound, WaveGateway.WAVES_IN_ADVANCE) - waves.size();
        for (int i = 0; i < missingWaves; ++i) {
            Wave wave = new Wave();
            wave.round = ++generatedWaves;
            wave.type = randomPlugin.get(RANDOM_WAVE_TYPES);
            wave.creepCount = wave.type.creepCount;
            wave.minSecondsToNextCreep = wave.type.getMinSecondsToNextCreep();
            wave.maxSecondsToNextCreep = wave.type.getMaxSecondsToNextCreep();

            addWave(wave);
        }
    }

    public void setTotalWaves(int totalWaves) {
        this.totalWaves = totalWaves;
    }

    public int getTotalWaves() {
        return totalWaves;
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public void setCurrentRound(int currentRound) {
        this.currentRound = currentRound;
    }

    public Wave getCurrentWave() {
        return currentWave;
    }
}
