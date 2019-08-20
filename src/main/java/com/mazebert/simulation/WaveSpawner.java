package com.mazebert.simulation;

import com.mazebert.simulation.countdown.BonusRoundCountDown;
import com.mazebert.simulation.countdown.EarlyCallCountDown;
import com.mazebert.simulation.countdown.TimeLordCountDown;
import com.mazebert.simulation.countdown.WaveCountDown;
import com.mazebert.simulation.gateways.*;
import com.mazebert.simulation.listeners.*;
import com.mazebert.simulation.plugins.random.RandomPlugin;
import com.mazebert.simulation.systems.ExperienceSystem;
import com.mazebert.simulation.systems.LootSystem;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.creeps.CreepType;
import com.mazebert.simulation.units.creeps.effects.TimeLordEffect;
import com.mazebert.simulation.units.items.ItemType;
import com.mazebert.simulation.units.wizards.Wizard;

import java.util.*;

public strictfp final class WaveSpawner implements OnGameStartedListener, OnWaveStartedListener, OnUpdateListener, OnDeadListener, OnUnitRemovedListener, OnTargetReachedListener, OnBonusRoundStartedListener, OnTimeLordStartedListener {
    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;
    private final WaveGateway waveGateway = Sim.context().waveGateway;
    private final UnitGateway unitGateway = Sim.context().unitGateway;
    private final RandomPlugin randomPlugin = Sim.context().randomPlugin;
    private final DifficultyGateway difficultyGateway = Sim.context().difficultyGateway;
    private final GameGateway gameGateway = Sim.context().gameGateway;
    private final LootSystem lootSystem = Sim.context().lootSystem;
    private final ExperienceSystem experienceSystem = Sim.context().experienceSystem;
    private final PlayerGateway playerGateway = Sim.context().playerGateway;

    private final int version = Sim.context().version;

    private Queue<Creep> creepQueue = new ArrayDeque<>();
    private Queue<Creep> goblinQueue = new ArrayDeque<>();
    private float countdownForNextCreepToSend;
    private float countdownForNextGoblinToSend;
    private boolean bonusRoundStarted;
    private double bonusRoundSeconds;
    private int currentBonusRound;

    public WaveSpawner() {
        simulationListeners.onGameStarted.add(this);
        simulationListeners.onWaveStarted.add(this);
        simulationListeners.onBonusRoundStarted.add(this);
        simulationListeners.onTimeLordStarted.add(this);
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

    @SuppressWarnings("Duplicates")
    @Override
    public void onUpdate(float dt) {
        if (!creepQueue.isEmpty()) {
            countdownForNextCreepToSend -= dt;
            if (countdownForNextCreepToSend <= 0.0f) {
                Creep creep = creepQueue.remove();
                spawnCreep(creep);

                countdownForNextCreepToSend = calculateCountdownForNextCreepToSend(creep.getWave());
            }
        }

        if (!goblinQueue.isEmpty()) {
            countdownForNextGoblinToSend -= dt;
            if (countdownForNextGoblinToSend <= 0.0f) {
                Creep creep = goblinQueue.remove();
                spawnCreep(creep);

                countdownForNextGoblinToSend = calculateCountdownForNextCreepToSend(creep.getWave());
            }
        }

        Game game = gameGateway.getGame();
        if (bonusRoundStarted) {
            bonusRoundSeconds += dt;
            int seconds = (int) bonusRoundSeconds;
            if (seconds > game.bonusRoundSeconds) {
                game.bonusRoundSeconds = seconds;
                simulationListeners.onBonusRoundSurvived.dispatch(seconds);

                if (experienceSystem.isTimeToGrantBonusRoundExperience(seconds)) {
                    unitGateway.forEach(Wizard.class, wizard -> experienceSystem.grantBonusRoundExperience(wizard, seconds));
                }

                if (seconds >= Balancing.TIME_LORD_ENCOUNTER_SECONDS && !game.timeLord && Sim.isDoLSeasonContent()) {
                    game.timeLord = true;

                    Sim.context().timeLordCountDown = new TimeLordCountDown();
                    Sim.context().timeLordCountDown.start();
                }

                if (seconds % Balancing.BONUS_SPAWN_COUNTDOWN_SECONDS == 0) {
                    spawnBonusRoundWave();
                }
            }
        }
    }

    public void spawnTreasureGoblins(Wizard wizard, int amount) {
        Wave wave = generateGoblinWave();

        for (int i = 0; i < amount; ++i) {
            Creep goblin = createGoblin(wizard, wave);
            goblinQueue.add(goblin);
        }
    }

    private Wave generateGoblinWave() {
        int round = waveGateway.getCurrentRound();
        if (version >= Sim.v13) {
            round += currentBonusRound;
        }

        return waveGateway.generateGoblinWave(round);
    }

    @SuppressWarnings("Duplicates")
    private Creep createGoblin(Wizard wizard, Wave wave) {
        double health = Balancing.getTotalCreepHitpoints(version, wave.round, difficultyGateway.getDifficulty());

        Creep goblin = new Creep();
        goblin.setWizard(wizard);
        goblin.setWave(wave);
        goblin.setHealth(health);
        goblin.setMaxHealth(health);
        goblin.setDropChance(4.0f);
        goblin.setMinDrops(1);
        goblin.setMaxDrops(4);
        goblin.setMaxItemLevel(wave.round);
        goblin.setGold(Balancing.getGoldForRound(wave.round, version));
        goblin.setArmor(wave.round + 50);
        goblin.setType(wave.creepType);
        return goblin;
    }

    public void spawnTreasureGoblin(Wizard wizard, int pathIndex, float x, float y) {
        Wave wave = generateGoblinWave();
        Creep goblin = createGoblin(wizard, wave);
        spawnCreep(goblin, pathIndex);
        goblin.setX(x);
        goblin.setY(y);
    }

    private float calculateCountdownForNextCreepToSend(Wave wave) {
        float countdown = randomPlugin.getFloat(wave.minSecondsToNextCreep, wave.maxSecondsToNextCreep);
        if (wave.creepCount > 1) {
            countdown /= playerGateway.getPlayerCount();
        }
        return countdown;
    }

    private void startNextWave() {
        if (Sim.context().earlyCallCountDown == null) {
            Sim.context().earlyCallCountDown = new EarlyCallCountDown();
            Sim.context().earlyCallCountDown.start();
            simulationListeners.onEarlyCallImpossible.dispatch();
        }

        Wave wave = waveGateway.nextWave();
        if (wave == null) {
            return;
        }

        if (version >= Sim.v16) {
            Sim.context().skippedSeconds += Balancing.WAVE_COUNTDOWN_SECONDS;
            Sim.context().simulationListeners.onSecondsSkipped.dispatch();
        }

        spawnWave(wave);

        waveGateway.generateMissingWaves(randomPlugin);
    }

    @SuppressWarnings("Duplicates")
    private void spawnWave(Wave wave) {
        int round = wave.round;

        double healthOfAllCreeps = Balancing.getTotalCreepHitpoints(version, round, difficultyGateway.getDifficulty());
        double healthOfOneCreep = getHealthOfOneCreep(wave, healthOfAllCreeps);

        int goldOfAllCreeps = Balancing.getGoldForRound(round, version);
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

        simulationListeners.onRoundStarted.dispatch(wave);
    }

    private double getHealthOfOneCreep(Wave wave, double healthOfAllCreeps) {
        double health = wave.type.getHealthMultiplier() * healthOfAllCreeps / wave.creepCount;
        if (version >= Sim.v15) {
            return health < 1.0 ? 1.0 : health;
        } else {
            return StrictMath.max(1, StrictMath.round(health));
        }
    }

    private void applyWaveAttributes(Creep creep, Wave wave) {
        if (gameGateway.getGame().bonusRound) {
            creep.setGold(0);
        } else {
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
        }

        creep.setType(wave.creepType);

        if (wave.creepModifier1 != null) {
            wave.creepModifier1.apply(creep);
            if (wave.creepModifier2 != null) {
                wave.creepModifier2.apply(creep);
            }
        }
    }

    private void spawnCreep(Creep creep) {
        spawnCreep(creep, 0);
    }

    private void spawnCreep(Creep creep, int pathIndex) {
        if (creep.getWave().type == WaveType.Air) {
            spawnCreep(creep, gameGateway.getMap().getAirPath(), pathIndex);
        } else {
            spawnCreep(creep, gameGateway.getMap().getGroundPath(), pathIndex);
        }
    }

    public void spawnCreep(Creep creep, Path path, int pathIndex) {
        creep.setPath(path, pathIndex);
        creep.onDead.add(this);
        creep.onTargetReached.add(this);

        unitGateway.addUnit(creep);
    }

    @Override
    public void onDead(Creep creep) {
        if (creep.isDead()) {
            unitGateway.removeUnit(creep);
        }
    }

    @Override
    public void onUnitRemoved(Unit unit) {
        if (unit instanceof Creep) {
            if (gameGateway.getGame().isLost()) {
                return;
            }

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

        if (isCurrentWaveComplete(wave)) {
            if (waveGateway.hasNextWave()) {
                Sim.context().waveCountDown = new WaveCountDown();
                Sim.context().waveCountDown.start();
            } else if (isGameWon()) {
                gameGateway.getGame().bonusRound = true;
                simulationListeners.onGameWon.dispatch();
                Sim.context().bonusRoundCountDown = new BonusRoundCountDown();
                Sim.context().bonusRoundCountDown.start();
            }
        }
    }

    private boolean isGameWon() {
        if (version < Sim.v13) {
            return true;
        }

        boolean livingGameCreeps = unitGateway.hasUnits(Creep.class, c -> c.getWave().origin == WaveOrigin.Game && !c.isDead());
        return !livingGameCreeps;
    }

    private boolean isCurrentWaveComplete(Wave wave) {
        if (version > Sim.v10) {
            return Sim.context().waveCountDown == null && (wave.round == waveGateway.getCurrentRound() || !waveGateway.hasNextWave()) && creepQueue.isEmpty();
        } else {
            return Sim.context().waveCountDown == null && (!unitGateway.hasUnits(Creep.class) || !waveGateway.hasNextWave()) && creepQueue.isEmpty();
        }
    }

    private void completeWave(Wizard wizard, Wave wave, Creep lastCreep) {
        if (simulationListeners.areNotificationsEnabled()) {
            simulationListeners.showNotification(wizard, "Completed round " + wave.round);
        }

        experienceSystem.grantExperience(wizard, wave, lastCreep);
        lootSystem.grantGoldInterest(wizard);
        lootSystem.researchTower(wizard, wave.round);

        if (playerGateway.getPlayerCount() == 1) {
            if (version >= Sim.v13) {
                if (wave.round == 200) {
                    lootSystem.addToStash(wizard, lastCreep, wizard.itemStash, ItemType.ScepterOfTime);
                }
            } else if (version >= Sim.v12) {
                if (wave.round == waveGateway.getTotalWaves()) {
                    lootSystem.addToStash(wizard, lastCreep, wizard.itemStash, ItemType.ScepterOfTime);
                }
            }
        }
    }

    @Override
    public void onTargetReached(Creep creep) {
        Wave wave = creep.getWave();
        if (wave.type == WaveType.TimeLord) {
            Sim.context().gameSystem.finishBonusRound();
        } else if (wave.type != WaveType.Challenge && wave.type != WaveType.MassChallenge) {
            Wizard wizard = creep.getWizard();
            float leaked = calculateLeaked(creep, wave);
            wizard.addHealth(-leaked);
        }

        if (creep.isPartOfGame()) {
            unitGateway.removeUnit(creep);
        }
    }

    private float calculateLeaked(Creep creep, Wave wave) {
        if (version >= Sim.v13) {
            return Balancing.PENALTY_FOR_LEAKING_ENTIRE_ROUND * (float) (creep.getHealth() / creep.getInitialHealth()) / wave.creepCount;
        } else {
            return Balancing.PENALTY_FOR_LEAKING_ENTIRE_ROUND * (float) (creep.getHealth() / creep.getMaxHealth()) / wave.creepCount;
        }
    }

    @Override
    public void onBonusRoundStarted() {
        bonusRoundStarted = true;
        spawnBonusRoundWave();
    }

    private void spawnBonusRoundWave() {
        if (gameGateway.getGame().health > 0.0f) {
            // Do not allow that the queue becomes too crowded
            if (creepQueue.size() < 50 * playerGateway.getPlayerCount()) {
                spawnWave(generateBonusRoundWave());
                ++currentBonusRound;
            }
        }
    }

    private Wave generateBonusRoundWave() {
        Wave wave = waveGateway.generateWave(randomPlugin, waveGateway.getTotalWaves() + currentBonusRound);
        if (!isWaveSuitableForBonusRound(wave.type)) {
            ++currentBonusRound;
            return generateBonusRoundWave();
        }

        if (wave.type == WaveType.Air) {
            wave.creepType = CreepType.Bat;
        } else {
            wave.creepType = CreepType.Troll;
        }

        wave.origin = WaveOrigin.BonusRound;

        return wave;
    }

    private boolean isWaveSuitableForBonusRound(WaveType type) {
        return type != WaveType.Horseman && type != WaveType.Challenge && type != WaveType.MassChallenge;
    }

    @Override
    public void onTimeLordStarted() {
        spawnTimeLordWave();
    }

    private void spawnTimeLordWave() {
        if (gameGateway.getGame().health > 0.0f) {
            Wave wave = waveGateway.generateTimeLordWave(waveGateway.getTotalWaves() + currentBonusRound);

            Creep creep = new Creep();
            creep.setWizard(null); // Time Lord is shared between all wizards!
            creep.setWave(wave);
            creep.setArmor(wave.round + 250);
            creep.setType(wave.creepType);

            creep.addAbility(new TimeLordEffect());

            creepQueue.add(creep);
        }
    }

}
