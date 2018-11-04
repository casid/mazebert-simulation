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
import org.jusecase.inject.Component;

import javax.inject.Inject;
import java.util.ArrayDeque;
import java.util.Queue;

@Component
public strictfp class WaveSpawner implements OnGameStartedListener, OnWaveStartedListener, OnUpdateListener, OnDeadListener, OnUnitRemovedListener, OnTargetReachedListener {
    @Inject
    private SimulationListeners simulationListeners;
    @Inject
    private WaveGateway waveGateway;
    @Inject
    private UnitGateway unitGateway;
    @Inject
    private RandomPlugin randomPlugin;
    @Inject
    private DifficultyGateway difficultyGateway;
    @Inject
    private GameGateway gameGateway;

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
        Wave wave = waveGateway.getNextWave();
        if (wave == null) {
            return;
        }

        int round = waveGateway.getCurrentWave();

        double healthOfAllCreeps = Balancing.getTotalCreepHitpoints(round, difficultyGateway.getDifficulty());
        double healthOfOneCreep = StrictMath.max(1, StrictMath.round(wave.healthMultiplier * healthOfAllCreeps / wave.creepCount));

        int goldOfAllCreeps = Balancing.getGoldForRound(round);
        int goldOfOneCreep = goldOfAllCreeps / wave.creepCount;
        int goldRemaining = goldOfAllCreeps % wave.creepCount;
        int creepIndexWithRemainingGold = (int)(randomPlugin.getFloatAbs() * wave.creepCount);

        for (int i = 0; i < wave.creepCount; ++i) {
            Creep creep = new Creep();
            creep.setWave(wave);
            creep.setHealth(healthOfOneCreep);
            creep.setMaxHealth(healthOfOneCreep);
            creep.setGold(i == creepIndexWithRemainingGold ? goldOfOneCreep + goldRemaining : goldOfOneCreep);
            creep.setArmor(round);
            creepQueue.add(creep);
        }

        waveGateway.generateMissingWaves(randomPlugin);
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
            Creep creep = (Creep)unit;
            if (--creep.getWave().creepCount <= 0) {
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
