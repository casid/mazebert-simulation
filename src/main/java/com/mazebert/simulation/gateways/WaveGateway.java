package com.mazebert.simulation.gateways;

import com.mazebert.simulation.*;
import com.mazebert.simulation.plugins.random.RandomPlugin;
import com.mazebert.simulation.units.creeps.CreepModifier;
import com.mazebert.simulation.units.creeps.CreepType;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Queue;

public strictfp class WaveGateway implements ReadonlyWaveGateway {
    public static final int WAVES_IN_ADVANCE = 3;
    private static final WaveType[] RANDOM_WAVE_TYPES = {WaveType.Normal, WaveType.Mass, WaveType.Boss, WaveType.Air};
    private static final CreepType[] RANDOM_AIR_CREEP_TYPES = {CreepType.AirDragon};
    private static final CreepType[] RANDOM_GROUND_CREEP_TYPES = {CreepType.Orc, CreepType.Rat, CreepType.Spider};
    private static final ArmorType[] RANDOM_ARMOR_TYPES = {ArmorType.Ber, ArmorType.Fal, ArmorType.Vex};

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

    public boolean hasNextWave() {
        return !waves.isEmpty();
    }

    @Override
    public Collection<Wave> getWaves() {
        return waves;
    }

    public void generateMissingWaves(RandomPlugin randomPlugin) {
        int missingWaves = StrictMath.min(totalWaves - currentRound, WaveGateway.WAVES_IN_ADVANCE) - waves.size();
        for (int i = 0; i < missingWaves; ++i) {
            Wave wave = generateWave(randomPlugin, ++generatedWaves);
            addWave(wave);
        }
    }

    public Wave generateWave(RandomPlugin randomPlugin, int round) {
        Wave wave = new Wave();
        wave.origin = WaveOrigin.Game;
        wave.round = round;
        wave.type = calculateWaveType(randomPlugin, round);
        wave.creepCount = wave.type.creepCount;
        wave.minSecondsToNextCreep = wave.type.getMinSecondsToNextCreep();
        wave.maxSecondsToNextCreep = wave.type.getMaxSecondsToNextCreep();
        wave.creepType = rollCreepType(wave, randomPlugin);
        wave.armorType = rollArmorType(wave, randomPlugin);
        wave.creepModifier1 = rollCreepModifier1(wave, randomPlugin);
        wave.creepModifier2 = rollCreepModifier2(wave, randomPlugin);
        return wave;
    }

    public Wave generateGoblinWave() {
        Wave wave = new Wave();
        wave.origin = WaveOrigin.Treasure;
        wave.round = getCurrentRound();
        wave.type = WaveType.Normal;
        wave.creepCount = wave.type.creepCount;
        wave.minSecondsToNextCreep = wave.type.getMinSecondsToNextCreep();
        wave.maxSecondsToNextCreep = wave.type.getMaxSecondsToNextCreep();
        wave.creepType = CreepType.Gnome;
        wave.armorType = ArmorType.Zod;
        return wave;
    }

    public WaveType calculateWaveType(RandomPlugin randomPlugin, int round) {
        if (round == 1 && Sim.context().gameSystem.isTutorial()) {
            return WaveType.Mass;
        }
        if (round > 0 && round % 50 == 0) {
            return WaveType.Horseman;
        }
        if (round > 0 && round % 14 == 0) {
            return WaveType.MassChallenge;
        }
        if (round > 0 && round % 7 == 0) {
            return WaveType.Challenge;
        }
        return randomPlugin.get(RANDOM_WAVE_TYPES);
    }

    private CreepType rollCreepType(Wave wave, RandomPlugin randomPlugin) {
        switch (wave.type) {
            case Air:
                return randomPlugin.get(RANDOM_AIR_CREEP_TYPES);
            case Horseman:
                return CreepType.Horseman;
        }
        return randomPlugin.get(RANDOM_GROUND_CREEP_TYPES);
    }

    private ArmorType rollArmorType(Wave wave, RandomPlugin randomPlugin) {
        switch (wave.type) {
            case Challenge:
            case MassChallenge:
            case Horseman:
                return ArmorType.Zod;
        }

        return randomPlugin.get(RANDOM_ARMOR_TYPES);
    }

    private CreepModifier rollCreepModifier1(Wave wave, RandomPlugin randomPlugin) {
        switch (wave.type) {
            case Challenge:
            case MassChallenge:
            case Horseman:
                return null;
        }

        if (randomPlugin.getFloatAbs() < 0.3f) {
            return null;
        }

        CreepModifier modifier = randomPlugin.get(CreepModifier.values());
        if (!modifier.isPossible(wave.round)) {
            return null;
        }

        return modifier;
    }

    private CreepModifier rollCreepModifier2(Wave wave, RandomPlugin randomPlugin) {
        if (wave.creepModifier1 == null) {
            return null;
        }

        if (randomPlugin.getFloatAbs() < 0.3f) {
            return null;
        }

        CreepModifier modifier = randomPlugin.get(CreepModifier.values());
        if (!modifier.isPossible(wave.round)) {
            return null;
        }

        if (!modifier.isCompatible(wave.creepModifier1)) {
            return null;
        }

        return modifier;
    }

    @Override
    public int getTotalWaves() {
        return totalWaves;
    }

    public void setTotalWaves(int totalWaves) {
        this.totalWaves = totalWaves;
    }

    @Override
    public int getCurrentRound() {
        return currentRound;
    }

    public void setCurrentRound(int currentRound) {
        this.currentRound = currentRound;
    }

    @Override
    public Wave getCurrentWave() {
        return currentWave;
    }
}
