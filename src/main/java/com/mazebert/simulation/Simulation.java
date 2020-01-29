package com.mazebert.simulation;

import com.mazebert.simulation.commands.Command;
import com.mazebert.simulation.commands.InitGameCommand;
import com.mazebert.simulation.commands.InitPlayerCommand;
import com.mazebert.simulation.errors.DsyncException;
import com.mazebert.simulation.gateways.*;
import com.mazebert.simulation.hash.DebugHash;
import com.mazebert.simulation.hash.Hash;
import com.mazebert.simulation.hash.HashHistory;
import com.mazebert.simulation.messages.Turn;
import com.mazebert.simulation.plugins.SleepPlugin;
import com.mazebert.simulation.projectiles.ProjectileGateway;
import com.mazebert.simulation.replay.ReplayReader;
import com.mazebert.simulation.replay.data.ReplayFrame;
import com.mazebert.simulation.replay.data.ReplayHeader;
import com.mazebert.simulation.units.Unit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public strictfp final class Simulation {

    public static final long TURN_TIME_IN_MILLIS = 100;

    private final CommandExecutor commandExecutor = Sim.context().commandExecutor;
    private final LocalCommandGateway localCommandGateway = Sim.context().localCommandGateway;
    private final TurnGateway turnGateway = Sim.context().turnGateway;
    private final SleepPlugin sleepPlugin = Sim.context().sleepPlugin;
    private final MessageGateway messageGateway = Sim.context().messageGateway;
    private final PlayerGateway playerGateway = Sim.context().playerGateway;
    private final UnitGateway unitGateway = Sim.context().unitGateway;
    private final ProjectileGateway projectileGateway = Sim.context().projectileGateway;
    private final ReplayWriterGateway replayWriterGateway = Sim.context().replayWriterGateway;
    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;
    private final GameGateway gameGateway = Sim.context().gameGateway;
    private final SimulationMonitor monitor = Sim.context().simulationMonitor;

    private long turnTimeInNanos = TimeUnit.MILLISECONDS.toNanos(TURN_TIME_IN_MILLIS);
    private float turnTimeInSeconds = TURN_TIME_IN_MILLIS / 1000.0f;
    private float unmodifiedTurnTimeInSeconds = turnTimeInSeconds;
    private float timeModifier = 1;
    private float timeDilation;
    private float playTimeInSeconds;
    private boolean pause;
    private boolean running = true;
    private boolean replayPause;
    private Hash hash = new Hash();

    public Simulation() {
        this(true);
    }

    public Simulation(boolean init) {
        if (init) {
            Sim.context().init(this);
        }
    }

    public void start(InitGameCommand initGameCommand, InitPlayerCommand initPlayerCommand) {
        if (replayWriterGateway.isWriteEnabled()) {
            ReplayHeader header = new ReplayHeader();
            header.version = Sim.version;
            header.playerId = playerGateway.getSimulationPlayerId();
            header.playerCount = playerGateway.getPlayerCount();
            header.season = Sim.context().season;
            replayWriterGateway.writeHeader(header);
        }

        scheduleInitGame(initGameCommand);
        scheduleInitPlayer(initPlayerCommand);
    }

    public void resume() {
        schedule(Collections.emptyList(), turnGateway.getCurrentTurnNumber());
        schedule(Collections.emptyList(), turnGateway.getCurrentTurnNumber() + 1);

        simulationListeners.onGameLoaded.dispatch();
    }

    private void scheduleInitGame(InitGameCommand initGameCommand) {
        List<Command> commands = new ArrayList<>();
        if (initGameCommand != null) {
            commands.add(initGameCommand);
        }
        schedule(commands, 0);
    }

    private void scheduleInitPlayer(InitPlayerCommand initPlayerCommand) {
        List<Command> commands = new ArrayList<>();
        commands.add(initPlayerCommand);
        schedule(commands, 1);
    }

    public boolean isRunning() {
        return running && !gameGateway.getGame().isLost();
    }

    public void stop() {
        if (replayWriterGateway.isWriteEnabled()) {
            hashGameState();
            replayWriterGateway.writeEnd(turnGateway.getCurrentTurnNumber(), hash.get());
            replayWriterGateway.close();
        }
    }

    public void setPause(int playerId, boolean pause) {
        if (this.pause != pause) {
            this.pause = pause;
            simulationListeners.onPause.dispatch(playerId, pause);
        }
    }

    @SuppressWarnings("unused")
    public void setReplayPause(boolean pause) {
        if (this.replayPause != pause) {
            this.replayPause = pause;
        }
    }

    @SuppressWarnings("unused")
    public boolean isReplayPause() {
        return replayPause;
    }

    public void setRunning(boolean running) {
        this.running = running;
        turnGateway.setRunning(running);
    }

    public void load(ReplayReader replayReader) {
        load(replayReader, Integer.MAX_VALUE);
    }

    public void load(ReplayReader replayReader, int turnNumber) {
        load(replayReader, turnNumber, false);
    }

    public void load(ReplayReader replayReader, int turnNumber, boolean debugDsync) {
        HashHistory hashHistory = new HashHistory(2);
        hashHistory.add(0);
        hashHistory.add(0);
        int lastValidTurnNumber = -1;

        while (isRunning()) {
            ReplayFrame replayFrame = replayReader.readFrame();
            if (replayFrame == null) {
                return;
            }
            if (replayFrame.turnNumber > turnNumber) {
                if (debugDsync) {
                    if (hash instanceof DebugHash) {
                        return; // We captured all turns until the dsync, time to abort
                    } else {
                        hash = new DebugHash();
                    }
                } else {
                    return;
                }
            }

            int turnNumbersToSimulate = replayFrame.turnNumber - turnGateway.getCurrentTurnNumber();
            for (int i = 0; i < turnNumbersToSimulate; ++i) {
                if (!isRunning()) {
                    return;
                }
                if (i >= turnNumbersToSimulate - 2) { // only the last two frames need to be hashed
                    simulateTurn(Collections.emptyList());
                    hashHistory.add(hash.get());
                } else {
                    simulateTurn(Collections.emptyList(), false);
                }
                turnGateway.incrementTurnNumber();
            }

            if (replayFrame.playerTurns != null) { // regular frame
                List<Turn> playerTurns = replayFrame.getTurns();
                simulateTurn(playerTurns);
                int expected = hashHistory.getOldest();
                if (replayFrame.hash != expected) {
                    throw new DsyncException("Oh shit, we have a dsync during loading. Expected: " + expected + ", actual: " + replayFrame.hash + "(My turn: " + replayFrame.turnNumber + ", theirs: " + replayFrame.turnNumber, lastValidTurnNumber);
                }
                hashHistory.add(hash.get());
                turnGateway.incrementTurnNumber();
                lastValidTurnNumber = replayFrame.turnNumber;
            } else { // last frame
                int expected = hashHistory.getNewest();
                if (replayFrame.hash != expected) {
                    throw new DsyncException("Oh shit, we have a dsync during loading. Expected: " + expected + ", actual: " + replayFrame.hash + "(My turn: " + replayFrame.turnNumber + ", theirs: " + replayFrame.turnNumber, lastValidTurnNumber);
                }
            }
        }
    }

    public void process() {
        if (replayPause) {
            sleepPlugin.sleepUntil(sleepPlugin.nanoTime(), turnTimeInNanos);
        } else {
            List<Turn> playerTurns = turnGateway.waitForAllPlayerTurns(messageGateway);
            if (running) {
                simulate(playerTurns);

                List<Command> commands = localCommandGateway.reset();
                schedule(commands, turnGateway.getTurnNumberForLocalCommands());

                turnGateway.incrementTurnNumber();
            }
        }
    }

    public void adjustSpeed(float factor) {
        setTimeModifier(timeModifier * factor);
    }

    public void setTimeModifier(float timeModifier) {
        this.timeModifier = timeModifier;
        turnTimeInSeconds = unmodifiedTurnTimeInSeconds * timeModifier;
    }

    @SuppressWarnings("unused") // use by client
    public boolean isPause() {
        return pause;
    }

    @SuppressWarnings("unused") // Used by client
    public float getTimeModifier() {
        if (pause || replayPause) {
            return 0;
        }
        return timeModifier;
    }

    public float getRawTimeModifier() {
        return timeModifier;
    }

    @SuppressWarnings("unused") // Used by client
    public float getTimeDilation() {
        return timeDilation;
    }

    public float getPlayTimeInSeconds() {
        return playTimeInSeconds;
    }

    private void simulate(List<Turn> playerTurns) {
        long start = sleepPlugin.nanoTime();

        int myHash = checkHashes(playerTurns);

        if (monitor.isRequired()) {
            synchronized (monitor.get()) {
                simulateTurn(playerTurns);
            }
        } else {
            simulateTurn(playerTurns);
        }

        if (replayWriterGateway.isWriteEnabled()) {
            replayWriterGateway.writeTurn(turnGateway.getCurrentTurnNumber(), playerTurns, myHash);
        }

        sleepPlugin.sleepUntil(start, turnTimeInNanos);
        long end = sleepPlugin.nanoTime();

        timeDilation = (float)turnTimeInNanos / (end - start);
    }

    private int checkHashes(List<Turn> playerTurns) {
        int myHash = 0;
        int myTurn = 0;
        for (Turn playerTurn : playerTurns) {
            if (playerTurn.playerId == playerGateway.getSimulationPlayerId()) {
                myHash = playerTurn.hash;
                myTurn = playerTurn.turnNumber;
            }
        }

        for (Turn playerTurn : playerTurns) {
            if (playerTurn.hash != myHash) {
                throw new DsyncException("Oh shit, we have a dsync with player " + playerTurn.playerId + ". Mine: " + myHash + ", theirs: " + playerTurn.hash + "(My turn: " + myTurn + ", theirs: " + playerTurn.turnNumber, turnGateway.getCurrentTurnNumber());
            }
        }

        return myHash;
    }

    private void simulateTurn(List<Turn> playerTurns) {
        simulateTurn(playerTurns, true);
    }

    public void simulateTurn(List<Turn> playerTurns, boolean hash) {
        simulatePlayerTurns(playerTurns);
        if (!pause) {
            simulateUnits();
            if (hash) {
                hashGameState();
            }

            playTimeInSeconds += turnTimeInSeconds;
        }
    }

    private void simulateUnits() {
        unitGateway.forEach(this::simulateUnit);
        projectileGateway.simulate(turnTimeInSeconds);
        simulationListeners.onUpdate.dispatch(turnTimeInSeconds);
    }

    private void simulateUnit(Unit unit) {
        unit.simulate(turnTimeInSeconds);
    }

    private void hashGameState() {
        hash.reset();
        hash.add(gameGateway.getGame());
        unitGateway.forEach(this::hashUnit);
    }

    private void hashUnit(Unit unit) {
        unit.hash(hash);
    }

    private void simulatePlayerTurns(List<Turn> playerTurns) {
        for (Turn playerTurn : playerTurns) {
            for (Command command : playerTurn.commands) {
                command.playerId = playerTurn.playerId;
                command.turnNumber = playerTurn.turnNumber;
                commandExecutor.executeVoid(command);
            }
        }
    }

    private void schedule(List<Command> commands, int turnNumber) {
        Turn turn = new Turn();
        turn.playerId = playerGateway.getSimulationPlayerId();
        turn.turnNumber = turnNumber;
        turn.commands = commands;
        turn.hash = hash.get();

        turnGateway.onLocalTurnReceived(turn);
        messageGateway.sendToOtherPlayers(turn);
    }
}
