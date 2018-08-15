package com.mazebert.simulation;

import com.mazebert.simulation.gateways.DifficultyGateway;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.gateways.WaveGateway;
import com.mazebert.simulation.listeners.OnDeadListener;
import com.mazebert.simulation.listeners.OnGameStartedListener;
import com.mazebert.simulation.listeners.OnUpdateListener;
import com.mazebert.simulation.plugins.random.RandomPlugin;
import com.mazebert.simulation.units.creeps.Creep;
import org.jusecase.inject.Component;

import javax.inject.Inject;
import java.util.ArrayDeque;
import java.util.Queue;

@Component
public strictfp class WaveSpawner implements OnGameStartedListener, OnUpdateListener, OnDeadListener {
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

    private Queue<Creep> creepQueue = new ArrayDeque<>();
    private float countdownForNextCreepToSend;

    public WaveSpawner() {
        simulationListeners.onGameStarted.add(this);
        simulationListeners.onUpdate.add(this);
    }

    @Override
    public void onGameStarted() {
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

        double healthOfAllCreeps = Balancing.getTotalCreepHitpoints(waveGateway.getCurrentWave(), difficultyGateway.getDifficulty());
        double healthOfOneCreep = StrictMath.max(1, StrictMath.round(wave.healthMultiplier * healthOfAllCreeps / wave.creepCount));

        int goldOfAllCreeps = Balancing.getGoldForRound(waveGateway.getCurrentWave());
        int goldOfOneCreep = goldOfAllCreeps / wave.creepCount;
        int goldRemaining = goldOfAllCreeps % wave.creepCount;
        int creepIndexWithRemainingGold = (int)(randomPlugin.getFloatAbs() * wave.creepCount);

        for (int i = 0; i < wave.creepCount; ++i) {
            Creep creep = new Creep();
            creep.setWave(wave);
            creep.setHealth(healthOfOneCreep);
            creep.setMaxHealth(healthOfOneCreep);
            creep.setGold(i == creepIndexWithRemainingGold ? goldOfOneCreep + goldRemaining : goldOfOneCreep);
            creepQueue.add(creep);
        }

        waveGateway.generateMissingWaves(randomPlugin);
    }

    private void spawnCreep(Creep creep) {
        creep.setX(5);
        creep.setY(5);
        creep.setPath(new Path(5, 5, 5, -5, -5, -5, 0, 0));
        creep.onDead.add(this);

        unitGateway.addUnit(creep);
        simulationListeners.onUnitAdded.dispatch(creep);
    }

    @Override
    public void onDead(Creep creep) {
        unitGateway.removeUnit(creep);
        simulationListeners.onUnitRemoved.dispatch(creep);
    }
}
