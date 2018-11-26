package com.mazebert.simulation;

import com.mazebert.simulation.countdown.WaveCountDown;
import com.mazebert.simulation.gateways.DifficultyGateway;
import com.mazebert.simulation.gateways.GameGateway;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.gateways.WaveGateway;
import com.mazebert.simulation.listeners.*;
import com.mazebert.simulation.plugins.random.RandomPlugin;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.creeps.Creep;

import java.util.ArrayDeque;
import java.util.Queue;

public strictfp class WaveSpawner implements OnGameStartedListener, OnWaveStartedListener, OnUpdateListener, OnDeadListener, OnUnitRemovedListener, OnTargetReachedListener {
    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;
    private final WaveGateway waveGateway = Sim.context().waveGateway;
    private final UnitGateway unitGateway = Sim.context().unitGateway;
    private final RandomPlugin randomPlugin = Sim.context().randomPlugin;
    private final DifficultyGateway difficultyGateway = Sim.context().difficultyGateway;
    private final GameGateway gameGateway = Sim.context().gameGateway;

    private Queue<Creep> creepQueue = new ArrayDeque<>();
    private float countdownForNextCreepToSend;

    public WaveSpawner() {
        simulationListeners.onGameStarted.add(this);
        simulationListeners.onWaveStarted.add(this);
        simulationListeners.onUpdate.add(this);
        simulationListeners.onUnitRemoved.add(this);
    }

    @Override
    public void onGameStarted() {
        startNextWave();
    }

    @Override
    public void onWaveStarted() {
        startNextWave();
    }

    @Override
    public void onUpdate(float dt) {
        if (creepQueue.isEmpty()) {
            return;
        }

        countdownForNextCreepToSend -= dt;
        if (countdownForNextCreepToSend <= 0.0f) {
            Creep creep = creepQueue.remove();
            spawnCreep(creep);

            countdownForNextCreepToSend = calculateCountdownForNextCreepToSend(creep.getWave());
        }
    }

    private float calculateCountdownForNextCreepToSend(Wave wave) {
        return randomPlugin.getFloat(wave.minSecondsToNextCreep, wave.maxSecondsToNextCreep);
    }

    private void startNextWave() {
        Wave wave = waveGateway.nextWave();
        if (wave == null) {
            return;
        }

        int round = waveGateway.getCurrentRound();

        double healthOfAllCreeps = Balancing.getTotalCreepHitpoints(round, difficultyGateway.getDifficulty());
        double healthOfOneCreep = StrictMath.max(1, StrictMath.round(wave.healthMultiplier * healthOfAllCreeps / wave.creepCount));

        int goldOfAllCreeps = Balancing.getGoldForRound(round);
        int goldOfOneCreep = goldOfAllCreeps / wave.creepCount;
        int goldRemaining = goldOfAllCreeps % wave.creepCount;
        int creepIndexWithRemainingGold = (int) (randomPlugin.getFloatAbs() * wave.creepCount);

        float experienceOfAllCreeps = Balancing.getExperienceForRound(round, wave.type);
        float experienceOfOneCreep = experienceOfAllCreeps / wave.creepCount;

        for (int i = 0; i < wave.creepCount; ++i) {
            Creep creep = new Creep();
            creep.setWave(wave);
            creep.setHealth(healthOfOneCreep);
            creep.setMaxHealth(healthOfOneCreep);
            creep.setGold(i == creepIndexWithRemainingGold ? goldOfOneCreep + goldRemaining : goldOfOneCreep);
            creep.setArmor(round);
            creep.setExperience(experienceOfOneCreep);
            applyWaveAttributes(creep, wave);

            creepQueue.add(creep);
        }

        waveGateway.generateMissingWaves(randomPlugin);
    }

    private void applyWaveAttributes(Creep creep, Wave wave) {
        switch (wave.type) {
            case Normal:
                creep.setDropChance(1.0f); // Normal drop chance.
                creep.setMinDrops(0); // No guaranteed drops.
                creep.setMaxDrops(2); // Maximum are two drops.
                creep.setMaxItemLevel(wave.round); // Item level is round.
                break;
            case Mass:
                creep.setDropChance(0.6f); // Reduced drop chance.
                creep.setMinDrops(0); // No guaranteed drops.
                creep.setMaxDrops(1); // Maximum is one drop.
                creep.setMaxItemLevel(wave.round); // Item level is round.
                break;
            case Boss:
                creep.setDropChance(5.0f); // Increased drop chance.
                creep.setMinDrops(0); // No guaranteed drops.
                creep.setMaxDrops(4); // Maximum are 4 drops.
                creep.setMaxItemLevel(wave.round + 2); // Item level is round + 2.
                break;
            case Air:
                creep.setDropChance(2.0f); // Slightly increased drop chance, as there are half as many air creeps than normal creeps to kill.
                creep.setMinDrops(0); // No guaranteed drops.
                creep.setMaxDrops(2); // Maximum are two drops.
                creep.setMaxItemLevel(wave.round); // Item level is round.
                break;
            case Challenge:
                creep.setDropChance(7.5f); // Increased drop chance.
                creep.setMinDrops(2); // Two guaranteed drops.
                creep.setMaxDrops(6); // Maximum are 6 drops.
                creep.setMaxItemLevel(wave.round + 5); // Item level is round + 5.
                break;
            case MassChallenge:
                creep.setDropChance(1.0f); // Normal drop chance.
                creep.setMinDrops(0); // No guaranteed drops.
                creep.setMaxDrops(2); // Maximum are two drops.
                creep.setMaxItemLevel(wave.round + 3); // Max Item level is round + 3.
                break;
            case Horseman:
                creep.setDropChance(5.0f); // Increased drop chance.
                creep.setMinDrops(2); // Two guaranteed drops.
                creep.setMaxDrops(4); // Maximum are 4 drops.
                creep.setMaxItemLevel(wave.round + 3); // Max Item level is round + 3
                break;
        }

        creep.setType(wave.creepType);
    }

    private void spawnCreep(Creep creep) {
        creep.setPath(gameGateway.getMap().getGroundPath());
        creep.onDead.add(this);
        creep.onTargetReached.add(this);

        unitGateway.addUnit(creep);
        simulationListeners.onUnitAdded.dispatch(creep);
    }

    @Override
    public void onDead(Creep creep) {
        removeCreep(creep);
    }

    @Override
    public void onUnitRemoved(Unit unit) {
        if (unit instanceof Creep) {
            Creep creep = (Creep) unit;
            if (--creep.getWave().creepCount <= 0 && !unitGateway.hasUnits(Creep.class)) {
                simulationListeners.onWaveFinished.dispatch();
                new WaveCountDown().start();
            }
        }
    }

    @Override
    public void onTargetReached(Creep creep) {
        removeCreep(creep);
    }

    private void removeCreep(Creep creep) {
        unitGateway.removeUnit(creep);
        simulationListeners.onUnitRemoved.dispatch(creep);
    }
}
