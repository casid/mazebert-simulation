package com.mazebert.simulation;

import com.mazebert.java8.Predicate;
import com.mazebert.simulation.countdown.*;
import com.mazebert.simulation.gateways.*;
import com.mazebert.simulation.listeners.*;
import com.mazebert.simulation.maps.MapGrid;
import com.mazebert.simulation.maps.Tile;
import com.mazebert.simulation.plugins.random.RandomPlugin;
import com.mazebert.simulation.systems.ExperienceSystem;
import com.mazebert.simulation.systems.LootSystem;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.creeps.CreepType;
import com.mazebert.simulation.units.creeps.effects.TimeLordArmorEffect;
import com.mazebert.simulation.units.creeps.effects.TimeLordEffect;
import com.mazebert.simulation.units.creeps.effects.TimeLordSpawnEffect;
import com.mazebert.simulation.units.items.ItemType;
import com.mazebert.simulation.units.wizards.Wizard;
import com.mazebert.simulation.usecases.NextWave;

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

    private final Queue<Creep> creepQueue = new ArrayDeque<>();
    private final Queue<Creep> extraCreepQueue = new ArrayDeque<>();
    private float countdownForNextCreepToSend;
    private float countdownForNextExtraCreepToSend;
    private boolean bonusRoundStarted;
    private double bonusRoundSeconds;
    private int lastBonusRoundSeconds;
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
        if (simulationListeners.areNotificationsEnabled() && gameGateway.getGame().isAprilFoolsGame()) {
            unitGateway.forEachWizard(wizard -> simulationListeners.showNotification(wizard, "All bosses that usually haunt TheMarine are after you now!"));
        }

        startNextWave(0);
    }

    @Override
    public void onWaveStarted(int skippedSeconds) {
        startNextWave(skippedSeconds);
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

        if (!extraCreepQueue.isEmpty()) {
            countdownForNextExtraCreepToSend -= dt;
            if (countdownForNextExtraCreepToSend <= 0.0f) {
                Creep creep = extraCreepQueue.remove();
                spawnCreep(creep);

                countdownForNextExtraCreepToSend = calculateCountdownForNextCreepToSend(creep.getWave());
            }
        }

        Game game = gameGateway.getGame();
        if (bonusRoundStarted) {
            bonusRoundSeconds += dt;
            int seconds = (int) bonusRoundSeconds;
            if (seconds > lastBonusRoundSeconds) {
                int delta = seconds - lastBonusRoundSeconds;
                lastBonusRoundSeconds = seconds;
                game.bonusRoundSeconds += delta;
                simulationListeners.onBonusRoundSurvived.dispatch(game.bonusRoundSeconds);

                if(!game.timeLord) {
                    if (experienceSystem.isTimeToGrantBonusRoundExperience(game.bonusRoundSeconds)) {
                        unitGateway.forEach(Wizard.class, wizard -> experienceSystem.grantBonusRoundExperience(wizard, game.bonusRoundSeconds, true));
                    }

                    if (game.isTimeLordAllowed() && game.bonusRoundSeconds >= Balancing.TIME_LORD_ENCOUNTER_SECONDS) {
                        startTimeLordCountDown();
                    }

                    if (seconds % Balancing.BONUS_SPAWN_COUNTDOWN_SECONDS == 0) {
                        spawnBonusRoundWave();
                    }
                }
            }
        }
    }

    public void startTimeLordCountDown() {
        creepQueue.clear();

        gameGateway.getGame().timeLord = true;

        Sim.context().timeLordCountDown = new TimeLordCountDown();
        Sim.context().timeLordCountDown.start();
    }

    public void spawnTreasureGoblins(Wizard wizard, int amount) {
        Wave wave = generateGoblinWave();

        for (int i = 0; i < amount; ++i) {
            Creep goblin = createGoblin(wizard, wave);
            extraCreepQueue.add(goblin);
        }
    }

    public void spawnExtraCultistsWave() {
        Wave wave = generateExtraCultistWave();
        spawnWaveNew(wave);
    }

    private Wave generateGoblinWave() {
        int round = waveGateway.getCurrentRound();
        if (version >= Sim.v13) {
            round += currentBonusRound;
        }

        return waveGateway.generateGoblinWave(round);
    }

    private Wave generateExtraCultistWave() {
        int round = waveGateway.getCurrentRound() + currentBonusRound;
        return waveGateway.generateExtraCultistWave(randomPlugin, round);
    }

    @SuppressWarnings("Duplicates")
    private Creep createGoblin(Wizard wizard, Wave wave) {
        double health = Balancing.getTotalCreepHitpoints(version, wave.round, difficultyGateway.getDifficulty(), playerGateway.getPlayerCount());

        Creep goblin = new Creep();
        goblin.setWizard(wizard);
        goblin.setWave(wave);
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

    private void startNextWave(int skippedSeconds) {
        Context context = Sim.context();

        if (context.earlyCallCountDown == null) {
            context.earlyCallCountDown = new EarlyCallCountDown();
            context.earlyCallCountDown.start();
            simulationListeners.onEarlyCallImpossible.dispatch();
        }

        Wave wave = waveGateway.nextWave();
        if (wave == null) {
            return;
        }

        if (version >= Sim.vRoCEnd) {
            if (context.stallingPreventionCountDown == null) {
                context.stallingPreventionCountDown = new StallingPreventionCountDown(wave);
                context.stallingPreventionCountDown.start();
            } else {
                context.stallingPreventionCountDown.reset();
            }

        }

        if (version >= Sim.vRoCEnd) {
            if (skippedSeconds > 0) {
                context.skippedSeconds += skippedSeconds;
                context.simulationListeners.onSecondsSkipped.dispatch();
            }
        } else if (version >= Sim.v16) {
            context.skippedSeconds += Balancing.WAVE_COUNTDOWN_SECONDS;
            context.simulationListeners.onSecondsSkipped.dispatch();
        }

        spawnWave(wave);

        waveGateway.generateMissingWaves(randomPlugin);
    }

    private void spawnWave(Wave wave) {
        if (version >= Sim.vDoLEnd) {
            spawnWaveNew(wave);
        } else {
            spawnWaveLegacy(wave);
        }
    }

    @SuppressWarnings("Duplicates")
    private void spawnWaveNew(Wave wave) {
        int round = wave.round;
        int playerCount = playerGateway.getPlayerCount();

        double healthOfAllCreeps = getHealthOfAllCreeps(round, playerCount, wave);
        double healthOfOneCreep = getHealthOfOneCreep(wave, healthOfAllCreeps);

        int goldOfAllCreeps = Balancing.getGoldForRound(round, version);
        int goldOfOneCreep = goldOfAllCreeps / wave.creepCount;
        int goldRemaining = goldOfAllCreeps % wave.creepCount;

        float experienceOfAllCreeps = Balancing.getExperienceForRound(version, round, wave.type, playerCount);
        float experienceOfOneCreep = experienceOfAllCreeps / wave.creepCount;

        int spawnCount = wave.creepCount;
        for (int i = 0; i < spawnCount; ++i) {
            Creep creep = new Creep();
            creep.setWave(wave);
            creep.setMaxHealth(healthOfOneCreep);
            if (goldRemaining > 0) {
                --goldRemaining;
                creep.setGold(goldOfOneCreep + 1);
            } else {
                creep.setGold(goldOfOneCreep);
            }
            creep.setArmor(round);
            creep.setExperience(experienceOfOneCreep);
            applyWaveAttributes(creep, wave);

            if (wave.origin != WaveOrigin.ExtraWave) {
                creepQueue.add(creep);
            } else {
                extraCreepQueue.add(creep);
            }
        }

        wave.remainingCreepCount = spawnCount;

        if (wave.origin != WaveOrigin.ExtraWave) {
            simulationListeners.onRoundStarted.dispatch(wave);
        }
    }

    private double getHealthOfAllCreeps(int round, int playerCount, Wave wave) {
        double result = Balancing.getTotalCreepHitpoints(version, round, difficultyGateway.getDifficulty(), playerCount);
        if (wave.type == WaveType.CultistOfYig) {
            result *= waveGateway.getCultistOfYigHealthMultiplier();
        }
        return result;
    }

    @SuppressWarnings("Duplicates")
    private void spawnWaveLegacy(Wave wave) {
        int round = wave.round;
        int playerCount = playerGateway.getPlayerCount();

        double healthOfAllCreeps = Balancing.getTotalCreepHitpoints(version, round, difficultyGateway.getDifficulty(), playerCount);
        double healthOfOneCreep = getHealthOfOneCreep(wave, healthOfAllCreeps);

        int goldOfAllCreeps = Balancing.getGoldForRound(round, version);
        int goldOfOneCreep = goldOfAllCreeps / wave.creepCount;
        int goldRemaining = goldOfAllCreeps % wave.creepCount;
        int creepIndexWithRemainingGold = (int) (randomPlugin.getFloatAbs() * wave.creepCount);

        float experienceOfAllCreeps = Balancing.getExperienceForRound(version, round, wave.type, playerCount);
        float experienceOfOneCreep = experienceOfAllCreeps / wave.creepCount;

        int spawnCount = wave.creepCount * playerCount;
        for (int i = 0; i < spawnCount; ++i) {
            int playerId = (i % playerCount) + 1;
            Wizard wizard = unitGateway.getWizard(playerId);

            Creep creep = new Creep();
            creep.setWizard(wizard);
            creep.setWave(wave);
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
        if (wave.type.isEldritch()) {
            switch (wave.type) {
                case CultistOfCthulhu:
                case CultistOfDagon:
                    creep.setDropChance(1.0f); // Normal drop chance.
                    creep.setMinDrops(0); // No guaranteed drops.
                    creep.setMaxDrops(3); // Maximum are three drops.
                    creep.setMaxItemLevel(wave.round + 1); // Item level is round + 1.
                    break;
                case CultistOfYig:
                    creep.setDropChance(0.75f); // Reduced drop chance.
                    creep.setMinDrops(0); // No guaranteed drops.
                    creep.setMaxDrops(1); // Maximum is one drop.
                    creep.setMaxItemLevel(wave.round); // Item level is round.
                    break;
                case CultistOfAzathoth:
                    creep.setDropChance(2.2f); // Slightly increased drop chance, as there are half as many air creeps than normal creeps to kill.
                    creep.setMinDrops(0); // No guaranteed drops.
                    creep.setMaxDrops(3); // Maximum are three drops.
                    creep.setMaxItemLevel(wave.round + 1); // Item level is round + 1.
                    break;
            }
        } else if (gameGateway.getGame().bonusRound) {
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
                    if (Sim.context().prophecySystem.isProphecyFulfilled(ItemType.WealthyBossProphecy)) {
                        creep.setMinDrops(1); // One guaranteed drops.
                        creep.setMaxDrops(5); // Maximum are 5 drops.
                        creep.setGold(0);
                    } else {
                        creep.setMinDrops(0); // No guaranteed drops.
                        creep.setMaxDrops(4); // Maximum are 4 drops.
                    }
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
        if (creep.isAir()) {
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
            if (gameGateway.getGame().isOver()) {
                return;
            }

            Creep creep = (Creep) unit;
            Wave wave = creep.getWave();

            if (wave.origin == WaveOrigin.Game && --wave.remainingCreepCount <= 0 && !wave.forcedCompletion) {
                completeWave(wave, creep);
            }
        }
    }

    public void completeWave(Wave wave, Creep lastCreep) {
        unitGateway.forEach(Wizard.class, wizard -> completeWave(wizard, wave, lastCreep));

        simulationListeners.onWaveFinished.dispatch(wave);

        if (isCurrentWaveComplete(wave)) {
            if (waveGateway.hasNextWave()) {
                Sim.context().waveCountDown = new WaveCountDown();
                Sim.context().waveCountDown.start();

                if (gameGateway.getGame().autoNextWave) {
                    NextWave.skipCountDown(Sim.context(), Sim.context().waveCountDown);
                }
            } else if (isBonusRoundStartAfterLastWave(wave)) {
                gameGateway.getGame().bonusRound = true;
                simulationListeners.onGameWon.dispatch();
                Sim.context().bonusRoundCountDown = new BonusRoundCountDown();
                Sim.context().bonusRoundCountDown.start();

                if (Sim.context().stallingPreventionCountDown != null) {
                    Sim.context().stallingPreventionCountDown.cancel();
                    Sim.context().stallingPreventionCountDown = null;
                }
            }
        }
    }

    private boolean isBonusRoundStartAfterLastWave(Wave wave) {
        if (version >= Sim.vRoCEnd && gameGateway.getGame().bonusRound) {
            return false; // TODO write test
        }

        if (version < Sim.v13) {
            return true;
        }

        if (wave.forcedCompletion) {
            return true;
        }

        boolean livingGameCreeps = unitGateway.hasUnits(Creep.class, c -> c.getWave().origin == WaveOrigin.Game && !c.isDead());
        return !livingGameCreeps;
    }

    private boolean isCurrentWaveComplete(Wave wave) {
        if (wave.forcedCompletion) {
            return true;
        }

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

        if (version < Sim.vDoLEnd && playerGateway.getPlayerCount() == 1) {
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
            float leaked = calculateLeaked(creep, wave);
            Wizard wizard = creep.getWizard();
            if (wizard == null) {
                float addHealth = -leaked / unitGateway.getWizardCount();
                unitGateway.forEachWizard(w -> w.addHealth(addHealth));
            } else {
                wizard.addHealth(-leaked);
            }
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
        Wave wave = waveGateway.generateWave(randomPlugin, waveGateway.getTotalWaves() + currentBonusRound, true);
        if (!isWaveSuitableForBonusRound(wave.type)) {
            ++currentBonusRound;
            return generateBonusRoundWave();
        }

        wave.creepType = getBonusRoundCreepType(wave);
        wave.origin = WaveOrigin.BonusRound;

        return wave;
    }

    private CreepType getBonusRoundCreepType(Wave wave) {
        if (wave.type == WaveType.Air) {
            return CreepType.Bat;
        }

        return CreepType.Troll;
    }

    private boolean isWaveSuitableForBonusRound(WaveType type) {
        switch (type) {
            case Normal:
            case Mass:
            case Boss:
            case Air:
                return true;
        }
        return false;
    }

    public void spawnTimeLordUnderlingsWave(Creep timeLord) {
        if (gameGateway.getGame().health > 0.0f) {
            Wave wave = generateTimeLordUnderlingsWave();
            ++currentBonusRound;

            Predicate<Tile> predicate = wave.type == WaveType.Air ? MapGrid.FLYABLE : MapGrid.WALKABLE;
            Path path = gameGateway.getMap().getGrid().findPath((int) timeLord.getX(), (int) timeLord.getY(), (int) timeLord.getTargetX(), (int) timeLord.getTargetY(), predicate);

            int round = wave.round;
            int playerCount = playerGateway.getPlayerCount();

            double healthOfAllCreeps = 0.4 * Balancing.getTotalCreepHitpoints(version, round, difficultyGateway.getDifficulty(), playerCount);
            double healthOfOneCreep = getHealthOfOneCreep(wave, healthOfAllCreeps);

            int spawnCount = wave.creepCount * playerCount;
            for (int i = 0; i < spawnCount; ++i) {
                int playerId = (i % playerCount) + 1;
                Wizard wizard = unitGateway.getWizard(playerId);

                Creep underling = new Creep();
                underling.setWizard(wizard);
                underling.setWave(wave);
                underling.setMaxHealth(healthOfOneCreep);
                underling.setArmor(round);
                applyWaveAttributes(underling, wave);

                spawnCreep(underling, path, 0);
                underling.setX(path.getX(0));
                underling.setY(path.getY(0));
            }
        }
    }

    private Wave generateTimeLordUnderlingsWave() {
        Wave wave = waveGateway.generateWave(randomPlugin, waveGateway.getTotalWaves() + currentBonusRound, true);
        if (!isWaveSuitableForTimeLordUnderling(wave.type)) {
            ++currentBonusRound;
            return generateTimeLordUnderlingsWave();
        }

        if (wave.type == WaveType.Air) {
            wave.creepType = CreepType.Bat;
        } else {
            wave.creepType = CreepType.Troll;
        }

        wave.origin = WaveOrigin.BonusRound;
        wave.creepCount /= 5;
        if (wave.creepCount < 1) {
            wave.creepCount = 1;
        }

        return wave;
    }

    private boolean isWaveSuitableForTimeLordUnderling(WaveType type) {
        if (gameGateway.getGame().isAprilFoolsGame()) {
            return true;
        }
        return type == WaveType.Air || type == WaveType.Mass || type == WaveType.Normal;
    }

    @Override
    public void onTimeLordStarted() {
        spawnTimeLordWave();
    }

    private void spawnTimeLordWave() {
        if (gameGateway.getGame().health > 0.0f) {
            Creep creep = createTimeLord();
            spawnCreep(creep);
        }
    }

    public Creep createTimeLord() {
        Wave wave = waveGateway.generateTimeLordWave(waveGateway.getTotalWaves() + currentBonusRound);

        Creep creep = new Creep();
        creep.setWizard(null); // Time Lord is shared between all wizards!
        creep.setWave(wave);
        creep.setArmor(wave.round + 250);
        creep.setType(wave.creepType);

        creep.addAbility(new TimeLordEffect());
        creep.addAbility(new TimeLordArmorEffect());
        creep.addAbility(new TimeLordSpawnEffect());
        return creep;
    }

}
