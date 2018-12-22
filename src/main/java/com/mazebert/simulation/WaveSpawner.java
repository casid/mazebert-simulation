package com.mazebert.simulation;

import com.mazebert.simulation.countdown.WaveCountDown;
import com.mazebert.simulation.gateways.*;
import com.mazebert.simulation.listeners.*;
import com.mazebert.simulation.plugins.random.RandomPlugin;
import com.mazebert.simulation.systems.ExperienceSystem;
import com.mazebert.simulation.systems.LootSystem;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.wizards.Wizard;

import java.util.ArrayDeque;
import java.util.Queue;

public strictfp class WaveSpawner implements OnGameStartedListener, OnWaveStartedListener, OnUpdateListener, OnDeadListener, OnUnitRemovedListener, OnTargetReachedListener {
    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;
    private final WaveGateway waveGateway = Sim.context().waveGateway;
    private final UnitGateway unitGateway = Sim.context().unitGateway;
    private final RandomPlugin randomPlugin = Sim.context().randomPlugin;
    private final DifficultyGateway difficultyGateway = Sim.context().difficultyGateway;
    private final GameGateway gameGateway = Sim.context().gameGateway;
    private final LootSystem lootSystem = Sim.context().lootSystem;
    private final ExperienceSystem experienceSystem = Sim.context().experienceSystem;
    private final PlayerGateway playerGateway = Sim.context().playerGateway;

    private Queue<Creep> creepQueue = new ArrayDeque<>();
    private Queue<Creep> goblinQueue = new ArrayDeque<>();
    private float countdownForNextCreepToSend;
    private float countdownForNextGoblinToSend;

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
        if (!creepQueue.isEmpty()) {
            countdownForNextCreepToSend -= dt;
            if (countdownForNextCreepToSend <= 0.0f) {
                Creep creep = creepQueue.remove();
                spawnCreep(creep);

                countdownForNextCreepToSend = calculateCountdownForNextCreepToSend(creep.getWave());
            }
        } else if (!goblinQueue.isEmpty()) {
            countdownForNextGoblinToSend -= dt;
            if (countdownForNextGoblinToSend <= 0.0f) {
                Creep creep = goblinQueue.remove();
                spawnCreep(creep);

                countdownForNextGoblinToSend = calculateCountdownForNextCreepToSend(creep.getWave());
            }
        }
    }

    public void spawnTreasureGoblins(Wizard wizard, int amount) {
        Wave wave = waveGateway.generateGoblinWave();

        for (int i = 0; i < amount; ++i) {
            Creep goblin = createGoblin(wizard, wave);
            goblinQueue.add(goblin);
        }
    }

    private Creep createGoblin(Wizard wizard, Wave wave) {
        double health = Balancing.getTotalCreepHitpoints(wave.round, difficultyGateway.getDifficulty());

        Creep goblin = new Creep();
        goblin.setWizard(wizard);
        goblin.setWave(wave);
        goblin.setHealth(health);
        goblin.setMaxHealth(health);
        goblin.setDropChance(4.0f);
        goblin.setMinDrops(1);
        goblin.setMaxDrops(4);
        goblin.setMaxItemLevel(wave.round);
        goblin.setGold(Balancing.getGoldForRound(wave.round));
        goblin.setArmor(wave.round + 50);
        goblin.setType(wave.creepType);
        return goblin;
    }

    public void spawnTreasureGoblin(Wizard wizard, int pathIndex) {
        Wave wave = waveGateway.generateGoblinWave();
        Creep goblin = createGoblin(wizard, wave);
        spawnCreep(goblin, pathIndex);
    }

    private float calculateCountdownForNextCreepToSend(Wave wave) {
        float countdown = randomPlugin.getFloat(wave.minSecondsToNextCreep, wave.maxSecondsToNextCreep);
        if (wave.creepCount > 1) {
            countdown /= playerGateway.getPlayerCount();
        }
        return countdown;
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

        int playerCount = playerGateway.getPlayerCount();
        int spawnCount = wave.creepCount * playerCount;
        for (int i = 0; i < spawnCount; ++i) {
            int playerId = (i % playerCount) + 1;
            Wizard wizard = unitGateway.getWizard(playerId);

            Creep creep = new Creep();
            creep.setWizard(wizard);
            creep.setWave(wave);
            creep.setHealth(healthOfOneCreep);
            creep.setMaxHealth(healthOfOneCreep);
            creep.setGold(i / playerCount == creepIndexWithRemainingGold ? goldOfOneCreep + goldRemaining : goldOfOneCreep);
            creep.setArmor(round);
            creep.setExperience(experienceOfOneCreep);
            applyWaveAttributes(creep, wave);

            creepQueue.add(creep);
        }

        wave.remainingCreepCount = spawnCount;

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
        spawnCreep(creep, 0);
    }

    private void spawnCreep(Creep creep, int pathIndex) {
        creep.setPath(gameGateway.getMap().getGroundPath(), pathIndex);
        creep.onDead.add(this);
        creep.onTargetReached.add(this);

        unitGateway.addUnit(creep);
    }

    @Override
    public void onDead(Creep creep) {
        unitGateway.removeUnit(creep);
    }

    @Override
    public void onUnitRemoved(Unit unit) {
        if (unit instanceof Creep) {
            Creep creep = (Creep) unit;
            Wave wave = creep.getWave();

            if (wave.origin == WaveOrigin.Game && --wave.remainingCreepCount <= 0) {
                completeWave(wave, creep);
            }
        }
    }

    private void completeWave(Wave wave, Creep lastCreep) {
        unitGateway.forEach(Wizard.class, wizard -> completeWave(wizard, wave, lastCreep));

        simulationListeners.onWaveFinished.dispatch(wave);

        if (Sim.context().waveCountDown == null && !unitGateway.hasUnits(Creep.class)) {
            Sim.context().waveCountDown = new WaveCountDown();
            Sim.context().waveCountDown.start();
        }
    }

    private void completeWave(Wizard wizard, Wave wave, Creep lastCreep) {
        if (simulationListeners.areNotificationsEnabled()) {
            simulationListeners.showNotification(wizard, "Completed round " + wave.round);
        }

        experienceSystem.grantExperience(wizard, wave, lastCreep);
        lootSystem.grantGoldInterest(wizard);
        lootSystem.researchTower(wizard, wave.round);
    }

    @Override
    public void onTargetReached(Creep creep) {
        Wave wave = creep.getWave();
        if (wave.type != WaveType.Challenge && wave.type != WaveType.MassChallenge) {
            Wizard wizard = creep.getWizard();
            float leaked = Balancing.PENALTY_FOR_LEAKING_ENTIRE_ROUND * (float)(creep.getHealth() / creep.getMaxHealth()) / wave.creepCount;
            wizard.addHealth(-leaked);
        }

        unitGateway.removeUnit(creep);
    }

}
