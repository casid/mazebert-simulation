package com.mazebert.simulation;

import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.gateways.WaveGateway;
import com.mazebert.simulation.listeners.OnGameStartedListener;
import com.mazebert.simulation.listeners.OnUpdateListener;
import com.mazebert.simulation.plugins.random.RandomPlugin;
import com.mazebert.simulation.units.creeps.Creep;
import org.jusecase.inject.Component;

import javax.inject.Inject;
import java.util.ArrayDeque;
import java.util.Queue;

@Component
public strictfp class WaveSpawner implements OnGameStartedListener, OnUpdateListener {
    @Inject
    private SimulationListeners simulationListeners;
    @Inject
    private WaveGateway waveGateway;
    @Inject
    private UnitGateway unitGateway;
    @Inject
    private RandomPlugin randomPlugin;

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

        for (int i = 0; i < wave.creepCount; ++i) {
            Creep creep = new Creep();
            creep.setWave(wave);
            creepQueue.add(creep);
        }

        waveGateway.generateMissingWaves(randomPlugin);
    }

    private void spawnCreep(Creep creep) {
        creep.setX(5);
        creep.setY(5);
        creep.setPath(new Path(5, 5, 5, -5, -5, -5, 0, 0));

        unitGateway.addUnit(creep);
        simulationListeners.onUnitAdded.dispatch(creep);
    }
}
